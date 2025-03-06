variable "aws_region" {
  description = "AWS region"
  default     = "sa-east-1"
}

variable "instance_type" {
  description = "EC2 instance type"
  default     = "t2.micro"
}

variable "key_name" {
  description = "Name of the SSH key pair"
  default = "gh_terraform"
}

variable "subnet_id" {
  description = "Subnet ID where the instance will be created"
  default = "subnet-0c6b03069499abf53"
}

variable "security_group_id" {
  description = "Security group ID for the instance"
  default = "sg-09b02afac31b0ee7c"
}

variable "instance_name" {
  description = "Tag name for the EC2 instance"
  default     = "leds-devops"
}

variable "ami_id" {
  description = "Amazon Machine Image ID"
  default = "ami-0780816dd7ce942fd"
}
