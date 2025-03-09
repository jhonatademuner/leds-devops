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

              echo "Running the whole script..."

              # Write the startup script to be executed at every boot
              cat << 'SCRIPT_EOF' > /var/lib/cloud/scripts/per-boot/startup.sh
              #!/bin/bash

              echo "Running the per-boot script..."

              # Check if Docker is installed
              if ! command -v docker &> /dev/null; then
                  echo "Docker not found. Installing..."
                  apt-get update
                  apt-get install -y docker.io
                  usermod -aG docker ubuntu
              else
                  echo "Docker is already installed."
              fi

              # Ensure no previous container is running
              docker stop leds-devops-container || true
              docker rm leds-devops-container || true

              # Pull and run Docker container
              docker pull ${var.docker_username}/leds-devops:latest
              docker run -d -p 8080:8080 --name leds-devops-container ${var.docker_username}/leds-devops:latest
              SCRIPT_EOF

              # Make the script executable
              chmod +x /var/lib/cloud/scripts/per-boot/startup.sh

              # Manually call the startup script for the first launch
              /var/lib/cloud/scripts/per-boot/startup.sh
              EOF

  tags = {
    Name = var.instance_name
  }
}
