<h1 align="center">
    <img alt="Hazelcast" title="Hazelcast" src=".github/logo.svg" width="250px" />
</h1>


<h4 align="center"> 
	Hazelcast ⚡
</h4>

![GitHub repo size](https://img.shields.io/github/repo-size/tacsio/hazelcast?color=%2331acbf)
![GitHub language count](https://img.shields.io/github/languages/count/tacsio/hazelcast?color=%2331acbf)
![GitHub top language](https://img.shields.io/github/languages/top/tacsio/hazelcast?color=%2331acbf)
![GitHub last commit](https://img.shields.io/github/last-commit/tacsio/hazelcast?color=%2331acbf)
[![GitHub issues](https://img.shields.io/github/issues-raw/tacsio/hazelcast?color=%2331acbf)](https://github.com/tacsio/hazelcast/issues)
[![GitHub contributors](https://img.shields.io/github/contributors/tacsio/hazelcast?color=%2331acbf)](https://github.com/tacsio/hazelcast/graphs/contributors)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/w/tacsio/hazelcast?color=%2331acbf)](https://github.com/tacsio/hazelcast/graphs/commit-activity)
![Maintenance](https://img.shields.io/maintenance/yes/2022?color=%2331acbf)


## Hazelcast

O Hazelcast é uma plataforma de computação e storage distribuído.

## Management Center

Na nova versão do Hazelcast (Hazelcast Platform) a interface de gerenciamento dos clusters é realizada através de uma aplicação extra. [[1]][management]

Para iniciar essa aplicação, basta executar o comando abaixo:

```docker
docker-compose up -d
```
Ele irá inicializar o management-center na porta *8080* e o usuário e senha 'default' será:

> username: admin
> 
> password: password12

![mc_1][mc_1]
![mc_2][mc_2]
![mc_3][mc_3]


## Projetos

Os projetos consistem em diversos testes de utilização do Hazelcast.

Estou utilizando um conjuto dados de exadristas e seus respectivos ratings FIDE em partidas classicas, rápidas e blitz (eu montei esse arquivo com dados possivelmente fora da realidade é provavel que alguns ratings estejam incorretos).

Para acessar via API basta seguir essa -tentativa de documentaçao-

| METHOD | PATHS       | ACTION                                                                                                                |
|--------|-------------|-----------------------------------------------------------------------------------------------------------------------|
| GET    | /load       | Carrega os dados dos enxadristas e seus respectivos ratings FIDE                                                      |
| GET    | /count      | Retorna a quantidade de enxadristas carregados                                                                        |
| GET    | /young      | Retorna todos com idade menor que 21 anos                                                                             |
| GET    | /name?like= | Retorna todos os enxadristas caso o nome contenha a string do parâmetro like'                                         |
| GET    | /classic    | Retorna o top 10 jogadores de clásico                                                                                 |
| GET    | /blitz      | Retorna o top 10 jogadores de blitz (acho que meus dados estão meio tronxos pq não vi o nome do Nakamura nessa lista) |


Não coloquei o top em partidas rápidas, pois como diria Danil Dubov...

[![Watch the video](https://img.youtube.com/vi/GRDO_WIxKkE/default.jpg)](https://youtu.be/GRDO_WIxKkE)

### External Hazelcast

Nesta versão, utilizamos um cluster de Hazelcast e conectamos uma aplicação Spring Boot.

Como o cluster aqui é externo é necessário executar o docker-compose no profile *full*

```bash
COMPOSE_PROFILES=full docker-compose up -d
```


Neste projeto tem um controller extra. Apenas para testar a lista do hazelcast [que não aparecec no management center]

| METHOD | PATHS    | ACTION                                                                        |
|--------|----------|-------------------------------------------------------------------------------|
| GET    | /lol     | Retorna a lista de todos os campeões cadastrados                              |
| GET    | /lol/new | Gera um campeão de League of Legends e adiciona na lista Hazelcast ditribuída |


> Configurações fora do caminho feliz:

Nesse projeto tive de fazer 2 configurações fora do default na ferramenta para funcionar do modo que eu queria.


1. O primeiro ponto é no próprio projeto Java. Tive de adicionar uma configuração extra no client adicionando as classes do projeto que seriam armazenadas no Hazelcast (o projeto quebrava na serialização).

O Hazelcast consegue serializar normalmente vários tipos da linguagem, para ele entender novos Tipos existem diversas formas, desde utilizar interfaces de serialização próprias a criar objetos 'portable'. Além de permitir que o servidor aceite deployment de classes e códigos. [[2]][serialization]

> Esse tipo de problema não ocorreu no modo embedded


```java
clientConfig.getUserCodeDeploymentConfig()
    .setEnabled(true)
    .addClass(Rating.class)
    .addClass(ChessPlayer.class);
```

2. Mesmo configurando no client, como essa feature não vem habilitada é preciso configurar a instância do Hazelcast Server para aceitar definições de classes do meu projeto, por isso existe um arquivo docker/config/hazelcast-config.xml que é basicamente a configuração padrão (existente no container do Hazelcast) acrescentando o seguinte bloco extra:

```xml
<user-code-deployment enabled="true">
    <class-cache-mode>ETERNAL</class-cache-mode>
    <provider-mode>LOCAL_AND_CACHED_CLASSES</provider-mode>
</user-code-deployment>
```


### Hazelcast Embbeded

Esta versão tem as funcionalidades básicas descritas na sessão Projetos, porém utiliza o hazelcast embbeded (não é necessário executar o docker-compose com as instâncias de hazelcast)

O ponto interessante aqui é que é necessário criar a configuração do Hazelcast Member (server), inclusive as opções de rede para que, caso novas instâncias de Hazelcast sejam encontradas, elam ingreesem automaticamente no cluster

```java
@Bean
public Config hazelcastConfig(NetworkConfig networkConfig,
                              ManagementCenterConfig managementCenterConfig,
                              Map<String, CacheSimpleConfig> cacheConfigs) {
    return new Config()
            .setClusterName("dev")
            .setNetworkConfig(networkConfig)
            .setManagementCenterConfig(managementCenterConfig)
            .setCacheConfigs(cacheConfigs)
            ;
}
```

### Spring Data Hazelcast

Essa versão surgiu inicialmente para testar o básico do starter do Hazelcast para o Spring Boot.

Teoricamente bastaria adicionar a dependência do hazelcast-spring que ao executar a aplicação, uma instância do Hazelcast já estaria disponível.

> Um detalhe interessante é que mesmo adicionando a versão 5.0.2 do hazelcast-spring ele irá instanciar uma a versão 4.x do IMDG e não a versão 5.x (Hazelcast Platform).

```xml
<dependency>
  <groupId>com.hazelcast</groupId>
  <artifactId>hazelcast-spring</artifactId>
  <version>5.0.2</version>
</dependency>
``` 

> Para usar já a versão correta, tive que adicionar também a blibloteca padrão do hazelcast.


```xml
<dependency>
  <groupId>com.hazelcast</groupId>
  <artifactId>hazelcast</artifactId>
  <version>5.0.2</version>
</dependency>
``` 

TODO: Este projeto ainda está em construção 


## Referências

[[1] - Hazelcast Management Center][management]

[[2] - Serialization][serialization]


[hazelcast]: https://docs.hazelcast.com/hazelcast/latest/index.html
[imdg]: https://docs.hazelcast.com/imdg/latest/
[jet]: https://jet-start.sh/docs/get-started/intro
[management]: https://docs.hazelcast.com/management-center/latest/getting-started/install
[serialization]: https://docs.hazelcast.com/hazelcast/latest/serialization/serialization

[mc_1]: https://github.com/tacsio/hazelcast-lab/raw/main/.github/mc_1.png
[mc_2]: https://github.com/tacsio/hazelcast-lab/raw/main/.github/mc_2.png
[mc_3]: https://github.com/tacsio/hazelcast-lab/raw/main/.github/mc_3.png
