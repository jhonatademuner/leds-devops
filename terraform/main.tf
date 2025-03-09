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

              # Write the script to the cloud-init per-boot directory
              cat > /var/lib/cloud/scripts/per-boot/user-script.sh << 'EOT'
              #!/bin/bash

              # Redirect all output to console (visible in EC2 system log)
              exec > >(tee /dev/console) 2>&1
              set -x

              # Log header with timestamp
              echo "===== STARTING PER-BOOT SCRIPT - $(date) ====="

              # Function to handle errors
              handle_error() {
                echo "ERROR at line $1: $2"
                exit 1
              }

              trap 'handle_error $LINENO "$BASH_COMMAND"' ERR

              # Docker installation block
              echo "[$(date)] Checking Docker installation..."
              if ! command -v docker &> /dev/null; then
                  echo "[$(date)] Installing Docker..."
                  sudo apt-get update
                  sudo apt-get install -y docker.io
                  sudo usermod -aG docker ubuntu
                  newgrp docker
                  echo "[$(date)] Docker installed successfully"
              else
                  echo "[$(date)] Docker already installed"
              fi

              # Container management block
              echo "[$(date)] Stopping existing container..."
              docker stop leds-devops-container || echo "No running container to stop"
              docker rm leds-devops-container || echo "No container to remove"

              echo "[$(date)] Pulling latest Docker image..."
              docker pull ${var.docker_username}/leds-devops:latest

              echo "[$(date)] Starting new container..."
              docker run -d -p 8080:8080 --name leds-devops-container ${var.docker_username}/leds-devops:latest

              echo "[$(date)] Script completed successfully"
              EOT

              # Make the script executable
              chmod +x /var/lib/cloud/scripts/per-boot/user-script.sh

              # Initial execution with logging
              echo "===== RUNNING INITIAL SETUP - $(date) =====" | tee /dev/console
              /var/lib/cloud/scripts/per-boot/user-script.sh
              EOF

  tags = {
    Name = var.instance_name
  }
}