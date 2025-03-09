provider "aws" {
  region = var.aws_region
}

resource "aws_instance" "ec2-instance" {
  ami                    = var.ami_id
  instance_type          = var.instance_type
  key_name               = var.key_name
  subnet_id              = var.subnet_id
  vpc_security_group_ids = [var.security_group_id]

  user_data = <<-EOF
              #!/bin/bash

              SCRIPT_PATH="/usr/local/bin/startup.sh"
              SERVICE_PATH="/etc/systemd/system/startup.service"

              # Create the startup script
              cat <<'EOT' > $SCRIPT_PATH
              #!/bin/bash
              echo "Running startup script" >> /var/log/startup.log

              # Check if Docker is installed
              if ! command -v docker &> /dev/null; then
                  echo "Docker not found. Installing..."
                  sudo apt-get update
                  sudo apt-get install -y docker.io
                  sudo usermod -aG docker $USER
                  newgrp docker
              else
                  echo "Docker is already installed."
              fi

              # Ensure no previous container is running
              docker stop leds-devops-container || true
              docker rm leds-devops-container || true

              # Pull and run Docker container
              docker pull ${var.docker_username}/leds-devops:latest
              docker run -d -p 8080:8080 --name leds-devops-container ${var.docker_username}/leds-devops:latest
              EOT

              # Make the script executable
              chmod +x $SCRIPT_PATH

              # Create a systemd service
              cat <<'EOT' > $SERVICE_PATH
              [Unit]
              Description=Run startup script on boot
              After=network.target

              [Service]
              Type=simple
              ExecStart=$SCRIPT_PATH
              Restart=always
              User=root

              [Install]
              WantedBy=multi-user.target
              EOT

              # Reload systemd, enable and start the service
              chmod 644 $SERVICE_PATH
              systemctl daemon-reload
              systemctl enable startup.service
              systemctl start startup.service

              EOF

  tags = {
    Name = var.instance_name
  }
}
