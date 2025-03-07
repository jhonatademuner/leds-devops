provider "aws" {
  region = var.aws_region
}

resource "aws_instance" "leds-devops" {
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

                # Create actions-runner directory and download the runner
                mkdir actions-runner && cd actions-runner
                curl -o actions-runner-linux-x64-2.322.0.tar.gz -L https://github.com/actions/runner/releases/download/v2.322.0/actions-runner-linux-x64-2.322.0.tar.gz
                echo "b13b784808359f31bc79b08a191f5f83757852957dd8fe3dbfcc38202ccf5768  actions-runner-linux-x64-2.322.0.tar.gz" | shasum -a 256 -c
                tar xzf ./actions-runner-linux-x64-2.322.0.tar.gz
                cd ~/actions-runner/
                ./config.sh --url https://github.com/jhonatademuner/leds-devops --token ${var.gh_token}
                ./run.sh
                sudo ./svc.sh install

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

              # Start actions-runner
              cd ~/actions-runner/
              sudo ./svc.sh start

              # Pull and run Docker container
              sudo docker pull ${var.docker_username}/leds-devops:latest
              sudo docker run -d -p 8080:8080 --name leds-devops-container ${var.docker_username}/leds-devops:latest
              EOF

  tags = {
    Name = var.instance_name
  }
}
