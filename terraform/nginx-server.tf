data "docker_image" "locimg_nginx_server" {
  name = "nginx-server:latest"
}

resource "docker_container" "nginx-server" {
  name = "nginx-server"
  image = data.docker_image.locimg_nginx_server.name

  ports {
    internal = 80
    external = 8000
  }
}
