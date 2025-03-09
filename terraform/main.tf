provider "aws" {
  region = var.aws_region
}

resource "aws_instance" "leds-devops" {
  ami                    = var.ami_id
  instance_type          = var.instance_type
  key_name               = var.key_name
  subnet_id              = var.subnet_id
  vpc_security_group_ids = [var.security_group_id]
  iam_instance_profile = aws_iam_instance_profile.leds_devops_challenge_ec2_instance_profile.name

  user_data = <<-EOF
              #!/bin/bash

              FLAG_FILE="/var/lib/my_first_run_complete"
              RUNNER_USER="ghuser"

              # Check if the flag file does not exists
              if [ ! -f "$FLAG_FILE" ]; then

                echo "Flag file not found. Running first-time initialization tasks..."

                echo "Creating user $RUNNER_USER..."
                # Create a new user without a password and default info.
                adduser --disabled-password --gecos "" "$RUNNER_USER"

                # Ensure the user is not in the sudo group if you want to keep it non-admin
                deluser "$RUNNER_USER" sudo

                # Create actions-runner directory and download the runner
                mkdir actions-runner && cd actions-runner
                curl -o actions-runner-linux-x64-2.322.0.tar.gz -L https://github.com/actions/runner/releases/download/v2.322.0/actions-runner-linux-x64-2.322.0.tar.gz
                echo "b13b784808359f31bc79b08a191f5f83757852957dd8fe3dbfcc38202ccf5768  actions-runner-linux-x64-2.322.0.tar.gz" | shasum -a 256 -c
                tar xzf ./actions-runner-linux-x64-2.322.0.tar.gz

                sudo -u $RUNNER_USER bash -c "./config.sh --url https://github.com/jhonatademuner/leds-devops --token ${var.gh_token} --unattended --name leds-ec2-ghrunner"

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

              echo "Running usual initialization tasks..."

              # Start actions-runner
              cd ~/../../actions-runner/
              sudo ./svc.sh start

              # Pull and run Docker container
              sudo docker pull ${var.docker_username}/leds-devops:latest
              sudo docker run -d -p 8080:8080 --name leds-devops-container ${var.docker_username}/leds-devops:latest

              # Mark configuration complete by tagging the instance (requires proper IAM permissions)
              INSTANCE_ID=$(curl -s http://169.254.169.254/latest/meta-data/instance-id)
              aws ec2 create-tags --resources $INSTANCE_ID --tags Key=ConfigurationStatus,Value=complete --region ${var.aws_region}
              EOF

  tags = {
    Name = var.instance_name
  }
}

resource "aws_iam_role" "leds_devops_challenge_ec2_role" {
  name = "leds_devops_challenge_ec2_role"

  assume_role_policy = <<EOF
  {
    "Version": "2012-10-17",
    "Statement": [
      {
        "Effect": "Allow",
        "Principal": {
          "Service": "ec2.amazonaws.com"
        },
        "Action": "sts:AssumeRole"
      }
    ]
  }
  EOF
}

resource "aws_iam_instance_profile" "leds_devops_challenge_ec2_instance_profile" {
  name = "leds_devops_challenge_ec2_instance_profile"
  role = aws_iam_role.leds_devops_challenge_ec2_role.name
}
