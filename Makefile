hazelcast-management:
	docker-compose up -d

hazelcast-full:
	COMPOSE_PROFILES=full docker-compose up -d

stop:
	COMPOSE_PROFILES=full docker-compose down

clean:
	COMPOSE_PROFILES=full docker-compose down -v