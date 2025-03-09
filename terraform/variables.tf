variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "sa-east-1"
}

variable "instance_type" {
  description = "EC2 instance type"
  type        = string
  default     = "t2.micro"
}

variable "key_name" {
  description = "Name of the SSH key pair"
  type        = string
  default     = "gh_terraform"
}

variable "subnet_id" {
  description = "Subnet ID where the instance will be created"
  type        = string
  default     = "subnet-0c6b03069499abf53"
}

variable "security_group_id" {
  description = "Security group ID for the instance"
  type        = string
  default     = "sg-09b02afac31b0ee7c"
}

variable "ami_id" {
  description = "Amazon Machine Image ID"
  type        = string
  default     = "ami-0780816dd7ce942fd"
}


variable "instance_name" {
  description = "Tag name for the EC2 instance"
  type        = string
}

variable "docker_username" {
  description = "Docker Hub Username"
  type        = string
}
