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
![Maintenance](https://img.shields.io/maintenance/yes/2020?color=%2331acbf)


## Hazelcast


## Projetos

### Hazelcast Embbeded

### Hazelcast Embbeded (Spring Library)

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

Para usar já a versão correta, tive que adicionar também a blibloteca padrão do hazelcast.


```xml
<dependency>
  <groupId>com.hazelcast</groupId>
  <artifactId>hazelcast</artifactId>
  <version>5.0.2</version>
</dependency>
``` 

Nesse projeto não utilizei algumas features da integração com o Spring Boot (que foi originalmente o objetivo desse sub-projeto).

### External Hazelcast