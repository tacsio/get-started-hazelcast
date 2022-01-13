hazelcast-management:
	docker-compose up -d

hazelcast-full:
	COMPOSE_PROFILES=full docker-compose up -d

clean:
	docker-compose down -v