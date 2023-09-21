config_deploy:
	sed -i "s/{{BUILD_NUMBER}}/${env.BUILD_NUMBER}/g" k8s/deployment.yml