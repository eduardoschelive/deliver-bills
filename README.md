# deliver-bills

Este é um CRUD de contas que eu fiz como teste para entrar na deliver-it



### Atenção
Neste repositório, se encontram duas pastas, a pasta `app/` é a aplicação frontend, onde temos todas as partes de interface, já na pasta `server/` é onde fica concentrada toda a regra de negócio. Almém disso na pasta `docker` você pode ver um exemplo de setup para rodar no docker.

### Acesso para um exemplo que eu já fiz deploy
[deliver-bills](https://xlr8code.com)
## Variáveis de ambiente

Para rodar esse projeto, você irá precisar das seguintes variáveis de ambiente

- Frontend (caso vá rodar localhost ou buildar):
```
    NEXT_PUBLIC_API_URL={IP DA PORTA}
```
Aponte ` NEXT_PUBLIC_API_URL` para o url em que o servidor estará hospedado

- Backend
```
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
```





## Rodando o projeto

Depois de clonar o repositório e fazer setup das variáveis de ambiente, primeiro você deve instalar as dependências do frontend, o projeto foi feito com node 20, vale ressaltar.

```
npm i
```

### Rodar o frontend
```
npm run dev
```

### Rodar o backend
O backend precisa de Java 21 para funcionar, depois de instalado, você pode apenas abrir o IntelliJ e clicar para rodar a main. Ou se quiser ir pelo caminho mais difícil:

```
mvn clean compile exec:java
```
ou
```
mvn clean package
java -jar {arquivo buildado aqui}
```
Atenção: Crie um diretório /var/log/deliver-bills para que a aplicação grave os logs, caso contrário ela só irá logar no console e haverá um erro na hora que aplicação subir, pode ignorar caso não queira criar o diretório

### Para rodar os testes
Também recomendo rodar os testes pelo IntelliJ, mas a linha de comando para rodar usando o `junit-platform-console-standalone` é
```
java -jar junit-platform-console-standalone-<version>.jar <Options>
```
Para rodar usando maven é
```
mvn clean test
```

### Também temos as imagens no DockerHub
- [guiraldelli/deliver-bills-app:latest](https://hub.docker.com/repository/docker/guiraldelli/deliver-bills-app/general)
- [guiraldelli/deliver-bills-server:latest](https://hub.docker.com/repository/docker/guiraldelli/deliver-bills-server/general)
