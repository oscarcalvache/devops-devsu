config_deploy:
	sed -i "s/{{BUILD_NUMBER}}/${BUILD_NUMBER}/g" k8s/deployment.yml