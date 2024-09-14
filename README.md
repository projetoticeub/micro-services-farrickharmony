<h1>1. Descrição Geral</h1>

<p>Este projeto consiste em uma API de Gerenciamento de Consultas Médicas que oferece funcionalidades para agendamento e gerenciamento de consultas entre Pacientes e Profissionais de Saúde. O sistema permite:</p>
<ul>
  <li>Cadastro de pacientes e profissionais de saúde.</li>
  <li>Agendamento de consultas, com validações de horários e disponibilidade.</li>
  <li>Listagem, atualização e exclusão de consultas.</li>
  <li>Envio de e-mails de confirmação de consulta usando RabbitMQ.</li>
  <li>Autenticação de usuários via JWT (JSON Web Token) para garantir a segurança da aplicação.</li>
  <li>Banco de dados gerenciado por PostgreSQL, com migrações automatizadas usando Flyway.</li>
  <li>Documentação automática da API gerada via Swagger para facilitar a exploração dos endpoints.</li>
</ul>

<h2>Tecnologias Utilizadas:</h2>
<ul>
  <li><strong>Spring Boot:</strong> Framework principal para desenvolvimento da API.</li>
  <li><strong>PostgreSQL:</strong> Banco de dados relacional utilizado para armazenar as informações do sistema.</li>
  <li><strong>Flyway:</strong> Ferramenta de versionamento do banco de dados, usada para gerenciar as migrações.</li>
  <li><strong>RabbitMQ:</strong> Sistema de mensageria utilizado para o envio assíncrono de e-mails.</li>
  <li><strong>Spring Security:</strong> Para segurança da aplicação e controle de autenticação/ autorização via JWT.</li>
  <li><strong>Swagger:</strong> Para documentação interativa da API.</li>
</ul>

<h2>Fluxo Geral:</h2>
<ul>
  <li>Usuários se autenticam na aplicação (registro/login) e recebem um token JWT.</li>
  <li>Com esse token, podem acessar os endpoints protegidos da aplicação.</li>
  <li>Os Pacientes e Profissionais de Saúde são cadastrados e mantidos atualizados no sistema.</li>
  <li>As Consultas são agendadas, e-mails de confirmação são enviados via RabbitMQ, e várias regras de validação garantem a integridade das operações.</li>
  <li>Todas as interações com o banco de dados são auditadas e seguras, utilizando JPA/Hibernate e as migrações do Flyway.</li>
</ul>

<h1>2. Criação dos Bancos</h1>

<p>Para que a aplicação funcione corretamente, é necessário criar dois bancos de dados:</p>
<ul>
  <li><strong>Banco "agenda":</strong> Este banco será utilizado para armazenar todas as informações relacionadas a pacientes, profissionais de saúde, consultas, e dados dos usuários.</li>
  <li><strong>Banco "agenda_email":</strong> Este banco será utilizado para armazenar o histórico de e-mails enviados pelo sistema, facilitando o controle de comunicação com os usuários.</li>
</ul>

<h2>Passos para criar os bancos:</h2>

<h3>Criando o banco "agenda" no PostgreSQL:</h3>
<p>Execute o seguinte comando no terminal para criar o banco de dados "agenda":</p>
<pre><code>CREATE DATABASE agenda;
</code></pre>

<h3>Criando o banco "agenda_email" no PostgreSQL:</h3>
<pre><code>CREATE DATABASE agenda_email;
</code></pre>

<h3>Configuração no <code>application.properties</code></h3>
<p>Depois de criar os bancos, configure as credenciais de conexão no arquivo <code>application.properties</code> da aplicação, apontando para o banco correto. Aqui está um exemplo:</p>

<pre><code># Configurações do banco de dados PostgreSQL para "agenda"
spring.datasource.url=jdbc:postgresql://localhost:5432/agenda
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# Configurações do banco de dados PostgreSQL para "agenda_email"
spring.datasource.url=jdbc:postgresql://localhost:5432/agenda_email
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
</code></pre>

<h1>3. Flyway</h1>

<p>O Flyway é utilizado para gerenciar as migrações do banco de dados. Ele mantém um histórico das alterações no banco e aplica as migrações necessárias ao iniciar a aplicação.</p>

<h2>Configuração do Flyway:</h2>
<p>O Flyway está configurado para rodar automaticamente ao iniciar a aplicação, aplicando os scripts de migração localizados no diretório <code>src/main/resources/db/migration</code>. Cada arquivo de migração segue uma convenção de nome:</p>

<ul>
  <li><code>V1__create-table-pacientes.sql</code>: Cria a tabela de pacientes.</li>
  <li><code>V2__create-table-profissionais_de_saude.sql</code>: Cria a tabela de profissionais de saúde.</li>
  <li><code>V3__create-table-usuarios.sql</code>: Cria a tabela de usuários.</li>
  <li><code>V4__create-table-consultas.sql</code>: Cria a tabela de consultas.</li>
</ul>

<p><strong>Nota:</strong> O Flyway utiliza a convenção <code>V{n}__nome-do-script.sql</code> para nomear os scripts de migração, onde <code>{n}</code> é o número da versão.</p>

<h1>4. JWT e Bcrypt</h1>

<p>O sistema de autenticação utiliza o <strong>JWT (JSON Web Token)</strong> para gerar tokens de acesso que garantem a segurança e a privacidade das requisições. Além disso, o <strong>Bcrypt</strong> é utilizado para criptografar as senhas dos usuários no momento do registro.</p>

<h2>Fluxo de Autenticação:</h2>
<ul>
  <li><strong>Registro:</strong> Quando um novo usuário se registra, a senha é criptografada com Bcrypt.</li>
  <li><strong>Login:</strong> No login, o token JWT é gerado para o usuário autenticado e retornado na resposta.</li>
  <li><strong>Autorização:</strong> Esse token é incluído no cabeçalho Authorization nas requisições subsequentes para acessar endpoints protegidos.</li>
</ul>

<h2>Exemplo de configuração do JWT:</h2>
<pre><code>api.security.token.secret=secreto-jwt</code></pre>

<h2>Exemplo de uso do Bcrypt no cadastro de usuários:</h2>
<pre><code>String senhaCodificada = new BCryptPasswordEncoder().encode(dados.senha());</code></pre>

<h1>5. Pacientes</h1>

<p>O módulo de Pacientes é responsável por todas as operações CRUD (Criar, Ler, Atualizar, Deletar) relacionadas aos pacientes. Além disso, ele permite buscar pacientes ativos e filtrar por diferentes critérios, como nome completo, CPF, data de nascimento e telefone.</p>

<h2>Endpoints de Pacientes</h2>
<p>Aqui está a lista dos endpoints disponíveis para o módulo de Pacientes:</p>
<ul>
  <li><strong>POST</strong> <code>/pacientes</code>: Cadastrar um novo paciente.</li>
  <li><strong>GET</strong> <code>/pacientes/{id}</code>: Buscar um paciente pelo seu ID.</li>
  <li><strong>GET</strong> <code>/pacientes</code>: Listar pacientes com filtros por nome, CPF, data de nascimento e telefone.</li>
  <li><strong>GET</strong> <code>/pacientes/active</code>: Listar pacientes ativos com paginação.</li>
  <li><strong>PUT</strong> <code>/pacientes/{id}</code>: Atualizar os dados de um paciente.</li>
  <li><strong>DELETE</strong> <code>/pacientes/{id}</code>: Excluir um paciente pelo ID.</li>
</ul>
<h2>Exemplo de Cadastro de Paciente</h2>

<p>Ao cadastrar um novo paciente, o sistema espera um JSON com a estrutura abaixo:</p>

<pre>
{
  "nomeCompleto": "Jane Doe",
  "cpf": "00000000000",
  "dataNascimento": "1990-05-20",
  "telefone": "61112345678",
  "email": "jane@example.com",
  "genero": "Feminino",
  "cep": "70000000",
  "endereco": "Rua 8, 500, Goiânia, GO"
}
</pre>

<p>Ao enviar esta requisição para o endpoint <strong>POST /pacientes</strong>, o paciente será cadastrado e o sistema retornará os dados completos com o ID gerado.</p>

<h2>Filtragem de Pacientes</h2>

<p>O endpoint <strong>GET /pacientes</strong> permite buscar pacientes usando filtros opcionais. Aqui está um exemplo de URL com parâmetros de filtro:</p>

<pre>GET /pacientes?nomeCompleto=Ana&cpf=12345678901</pre>

<p>Isso retornará uma lista paginada de pacientes que correspondem ao nome completo "Ana" e ao CPF "12345678901".</p>

<h3>Estrutura da Tabela de Pacientes</h3>

<p>Aqui está a estrutura básica da tabela de Pacientes, conforme definida no arquivo de migração do Flyway:</p>

<pre>
CREATE TABLE pacientes (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(15),
    email VARCHAR(255) UNIQUE,
    genero VARCHAR(10),
    cep VARCHAR(8),
    endereco TEXT,
    ativo BOOLEAN NOT NULL DEFAULT true
);
</pre>

<h2>Profissionais de Saúde</h2>

<p>O módulo de Profissionais de Saúde permite o gerenciamento de todos os profissionais que podem atender os pacientes. Ele possui as mesmas funcionalidades de CRUD que o módulo de pacientes, além de permitir filtrar por critérios como nome completo, CPF, data de nascimento, telefone e registro profissional.</p>

<h3>Endpoints de Profissionais de Saúde</h3>

<ul>
  <li><strong>POST /profissionais-de-saude</strong>: Cadastrar um novo profissional de saúde.</li>
  <li><strong>GET /profissionais-de-saude/{id}</strong>: Buscar um profissional pelo ID.</li>
  <li><strong>GET /profissionais-de-saude</strong>: Listar profissionais de saúde com filtros por nome, CPF, telefone, e registro.</li>
  <li><strong>GET /profissionais-de-saude/active</strong>: Listar profissionais de saúde ativos.</li>
  <li><strong>PUT /profissionais-de-saude/{id}</strong>: Atualizar um profissional de saúde.</li>
  <li><strong>DELETE /profissionais-de-saude/{id}</strong>: Excluir um profissional pelo ID.</li>
</ul>
<h3>Exemplo de Cadastro de Profissional de Saúde</h3>

<p>A estrutura JSON esperada para cadastrar um novo profissional é a seguinte:</p>

<pre>
{
  "nomeCompleto": "John",
  "cpf": "98765432100",
  "dataNascimento": "1975-10-30",
  "telefone": "62987653210",
  "registro": "123456",
  "email": "john@example.com",
  "genero": "Masculino",
  "cep": "01001001",
  "endereco": "Avenida Paulista, 1000, São Paulo, SP"
}
</pre>

<h3>Estrutura da Tabela de Profissionais de Saúde</h3>

<p>A tabela Profissionais de Saúde possui uma estrutura similar à de Pacientes, mas inclui um campo adicional para o registro profissional:</p>

<pre>
CREATE TABLE profissionais_de_saude (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    registro VARCHAR(50),
    genero VARCHAR(20),
    cep VARCHAR(8),
    endereco TEXT,
    ativo BOOLEAN NOT NULL
);
</pre>

<h3>Consultas</h3>

<p>O módulo de Consultas permite agendar, atualizar, listar e excluir consultas médicas. As consultas possuem várias validações, como disponibilidade do profissional e horários de funcionamento.</p>

<h4>Endpoints de Consultas</h4>
<ul>
    <li><strong>POST /consultas:</strong> Agendar uma nova consulta.</li>
    <li><strong>GET /consultas/{id}:</strong> Buscar uma consulta pelo ID.</li>
    <li><strong>GET /consultas:</strong> Listar consultas com filtros por profissional, paciente e data.</li>
    <li><strong>GET /consultas/data/{data}:</strong> Listar consultas por data específica.</li>
    <li><strong>PUT /consultas/{id}:</strong> Atualizar a data de uma consulta.</li>
    <li><strong>DELETE /consultas/{id}:</strong> Excluir uma consulta pelo ID.</li>
</ul>

<h4>Exemplo de Agendamento de Consulta</h4>

<p>Aqui está a estrutura JSON para agendar uma nova consulta:</p>

<pre>
{
    "idPaciente": 1,
    "idProfissionalDeSaude": 1,
    "data": "2024-08-31T09:04"
}
</pre>

<h4>Validações ao Agendar Consultas</h4>

<p>A aplicação possui várias validações ao agendar uma consulta, como:</p>
<ul>
    <li><strong>Validação de horário de antecedência:</strong> A consulta precisa ser marcada com no mínimo 10 minutos de antecedência.</li>
    <li><strong>Validação de horário de funcionamento:</strong> As consultas só podem ser agendadas entre 8h e 17h, e não são permitidas aos domingos.</li>
    <li><strong>Validação de disponibilidade do profissional:</strong> Um profissional não pode ter mais de uma consulta no mesmo horário.</li>
    <li><strong>Validação de consultas do paciente:</strong> Um paciente não pode ter mais de uma consulta no mesmo dia.</li>
</ul>

<h4>Estrutura da Tabela de Consultas</h4>

<p>A tabela de consultas é definida com chaves estrangeiras para os pacientes e os profissionais de saúde:</p>

<pre>
CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,
    profissional_de_saude_id BIGINT REFERENCES profissionais_de_saude(id) ON DELETE SET NULL,
    paciente_id BIGINT REFERENCES pacientes(id) ON DELETE SET NULL,
    data TIMESTAMP NOT NULL,
    descricao TEXT
);
</pre>

<h4>Usuários</h4>

<p>O módulo de Usuários gerencia o registro e login na aplicação. Cada usuário possui um login e uma senha criptografada com Bcrypt. Após o login, um token JWT é gerado e usado para autenticação nas requisições seguintes.</p>

<h5>Endpoints de Usuários</h5>
<ul>
    <li><strong>POST /auth/registrar:</strong> Registrar um novo usuário.</li>
    <li><strong>POST /auth/login:</strong> Autenticar o usuário e gerar um token JWT.</li>
</ul>

<h5>Exemplo de Registro de Usuário</h5>
<p>Ao registrar um novo usuário, o sistema espera um JSON com a seguinte estrutura:</p>
<pre>
{
    "login": "admin",
    "senha": "admin",
    "role": "ADMIN"
}
</pre>

<h5>Estrutura da Tabela de Usuários</h5>
<p>A tabela de Usuários armazena o login e a senha criptografada:</p>

<pre>
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);
</pre>

<h4>Email</h4>

<p>O sistema implementa uma funcionalidade de envio de e-mails utilizando o RabbitMQ para agendamento e processamento assíncrono de mensagens. A tabela <code>emails</code> foi criada para armazenar as informações referentes aos e-mails enviados, relacionados às consultas agendadas no sistema.</p>

<h5>Estrutura da Tabela emails</h5>
<p>Aqui está a definição SQL da tabela <code>emails</code>:</p>

<pre>
CREATE TABLE emails (
    id BIGSERIAL PRIMARY KEY,
    id_consulta BIGINT NOT NULL,
    id_paciente BIGINT NOT NULL,
    email_from VARCHAR(255) NOT NULL,
    email_to VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    send_data_email TIMESTAMP NOT NULL,
    status_email SMALLINT NOT NULL
);

ALTER TABLE emails
  ALTER COLUMN text TYPE oid
  USING text::oid;
</pre>

<h4>10. Swagger</h4>
<p>
A aplicação utiliza o <strong>Swagger</strong> para documentar todos os endpoints e permitir que os desenvolvedores explorem a API de forma interativa. Ao acessar o endpoint <code>/swagger-ui.html</code>, a documentação é exibida, mostrando todos os métodos HTTP disponíveis, suas descrições e parâmetros.
</p>

<h4>11. Exceptions</h4>
<p>
A aplicação trata as exceções de forma centralizada, garantindo que erros comuns como validações e requisições mal formatadas sejam capturados e retornem uma resposta apropriada ao cliente, com códigos HTTP e mensagens de erro claras.
</p>

<h4>12. Interface de Validação</h4>
<p>
Para garantir a consistência nos agendamentos, foi criada uma interface de validação que é implementada por diversas classes de validação, como Validador de Horário de Funcionamento e Validador de Disponibilidade do Profissional. Essas classes implementam a lógica de validação antes de agendar uma consulta.
</p>

<h2>13. Envio de Mensagens com RabbitMQ</h2>
<p>O <strong>RabbitMQ</strong> é usado para enviar mensagens assíncronas, como e-mails de confirmação de agendamento de consultas. Quando uma consulta é agendada, uma mensagem é enviada para a fila de e-mails, e o sistema de consumo de mensagens (<em>consumer</em>) processa o envio do e-mail.</p>

<h3>Fila <em>default.email</em></h3>
<p>A fila <strong>default.email</strong> está configurada no Virtual Host <strong>fzimrir</strong>. A criação de filas em diferentes Virtual Hosts permite a segmentação de diferentes ambientes ou funcionalidades dentro da mesma instância do RabbitMQ. No caso da fila em questão:</p>

<ul>
  <li><strong>Nome da Fila:</strong> default.email</li>
  <li><strong>Tipo de Fila:</strong> Classic (fila padrão do RabbitMQ)</li>
  <li><strong>Virtual Host:</strong> fzimrir</li>
</ul>

<p>Essa fila está configurada para gerenciar o envio de emails, conforme indicado pelo seu nome. A fila utiliza a política de limite <strong>fzimrir-max-length</strong>, o que define um máximo de mensagens que podem ser armazenadas na fila ao mesmo tempo, evitando sobrecarga de armazenamento.</p>

<h2>Portas</h2>
<p>A aplicação roda nas portas <strong>8080</strong> e <strong>8081</strong>, conforme especificado no <code>application.properties</code>.</p>
<p>O front-end está configurado para rodar na porta <strong>4200</strong>, e as configurações de <strong>CORS</strong> permitem requisições vindas desta origem.</p>

