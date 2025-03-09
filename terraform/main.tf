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

              FLAG_FILE="/var/lib/my_first_run_complete"

              # Check if the flag file does not exists
              if [ ! -f "$FLAG_FILE" ]; then

                echo "Flag file not found. Running first-time initialization tasks..."


                # Install Docker
                sudo apt-get update
                sudo apt-get install docker.io -y
                sudo usermod -aG docker $USER
                newgrp docker

                # Mark that the initialization has run by creating the flag file
                touch "$FLAG_FILE"
              else
                echo "Flag file exists. Skipping first-time initialization."
              fi

              echo "Running usual initialization tasks..."

              # Pull and run Docker container
              sudo docker pull ${var.docker_username}/leds-devops:latest
              sudo docker run -d -p 8080:8080 --name leds-devops-container ${var.docker_username}/leds-devops:latest
              EOF

  tags = {
    Name = var.instance_name
  }
}
