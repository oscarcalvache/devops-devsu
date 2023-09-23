build_config:
	sed -i "s/{{DEVSU_DATASURCE_URL}}/${DEVSU_DATASORCE_URL}/g" src/main/resources/application.properties
	sed -i "s/{{DEVSU_DB_USER}}/${DEVSU_DB_USER}/g" src/main/resources/application.properties
	sed -i "s/{{DEVSU_DB_PASSWORD}}/${DEVSU_DB_PASSWORD}/g" src/main/resources/application.properties

config_deploy:
	sed -i "s/{{BUILD_NUMBER}}/${BUILD_NUMBER}/g" k8s/deployment.yml