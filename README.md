# 🧠 ABCDE — Leitura de Gabaritos

**Projeto Freelancer desenvolvido individualmente** para a empresa **[Computex Sistemas Acadêmicos LTDA]**, com o objetivo de **automatizar a leitura e processamento de gabaritos escaneados** (tipos ABCDE e Vestibular).

O sistema realiza a leitura das marcações feitas pelos(as) alunos(as) em gabaritos físicos, identifica as respostas e **retorna os resultados automaticamente** via **requisição POST** para um endpoint configurado pelo cliente e também em formato **.txt**.

---

## 🚀 Tecnologias Utilizadas

- **Back-end:** (`Spring Boot`, `Python`)
- **Banco de Dados:** (`PostgreSQL`)
- **Autenticação:** JWT + Criptografia de senhas (`bcrypt`)
- **Envio de e-mails:** `SMTP utilizando a SENDGRID`
- **Leitura de imagens:** (Python: `OpenCV`, `numpy`, `pyzbar`, `uvicorn`, `fastapi`, dentre outras...)
- **Hospedagem:** (Subi no servidor LINUX da empresa, que utiliza a FIX. Compilei o projeto em SPRING, buildei o projeto em angular e os peguei arquivos do ambiente virtual `.venv` da API python para leitura das imagens e está disponível através do endereço [https://abcde.computex.com.br](https://abcde.computex.com.br/))

---

## 🧩 Principais Funcionalidades

### 🔐 Sistema de Autenticação com 3 Níveis de Acesso
- **COMPUTEX:** administração completa de clientes e seus usuários.
- **CLIENTE:** gerenciamento de seus próprios usuários, permissões e lotes de gabaritos.
- **USUÁRIO DO CLIENTE:** acesso controlado conforme permissões definidas pelo cliente.

**Permissões configuráveis:**
- Apenas leitura de lotes/imagens  
- Apenas criação de lotes/imagens  
- Leitura e criação de lotes/imagens

**Detalhes adicionais:**
- Login via **CNPJ** (para clientes) e **e-mail** (para usuários).
- CNPJ do tipo **COMPUTEX** pode gerenciar outros usuários COMPUTEX.
- **Senhas criptografadas** e redefinição de senha via e-mail seguro.

---

### 🧾 Gestão de Lotes e Imagens
- Criação, leitura, atualização e exclusão (CRUD completo) de **lotes de gabaritos**.
- Upload e armazenamento de imagens escaneadas (tipos ABCDE e Vestibular).
- Processamento automático para leitura das marcações dos(as) alunos(as).
- Retorno das informações processadas via:
  - **POST** para endpoint configurado no cadastro do cliente.
  - **Arquivo `.txt`** contendo os resultados detalhados.

---

### 🕵️‍♂️ Auditoria Completa
- Registro de todas as ações realizadas no sistema (CRUD e autenticação).
- Histórico detalhado por usuário, lote e imagem.
- Facilita rastreabilidade e segurança das operações.

---

## ⚙️ Fluxo do Sistema (Resumo)

1. **Cliente cria um lote** e envia as imagens dos gabaritos.
2. **Sistema processa as imagens**, identifica as marcações e as transforma em dados estruturados.
3. **Resultados retornam** automaticamente:
   - Via **POST** para o endpoint configurado no cadastro do cliente.
   - Via **arquivo .txt** armazenado e disponibilizado no painel do cliente.
4. **Auditoria registra** todas as operações realizadas pelos usuários.

---

## 🧰 Segurança e Boas Práticas
- Senhas armazenadas com **hash seguro**.
- Tokens JWT para controle de sessão.
- Rotas protegidas e controle de acesso por **papel e permissão**.
- Logs e auditoria detalhada para monitoramento.

---

## 📈 Impacto do Projeto

✅ Redução significativa do tempo de correção de provas.  
✅ Eliminação de erros manuais na leitura de marcações.  
✅ Processos automatizados e integrados com sistemas externos.  
✅ Aumento da eficiência para escolas e instituições parceiras da COMPUTEX.

---

## 👨‍💻 Autor

**Desenvolvido por Francisco Lucas**  
💼 Projeto freelancer desenvolvido **individualmente**, fora da empresa, para **Computex Sistemas Acadêmicos LTDA**.

🔗 [LinkedIn](https://www.linkedin.com/in/seu-perfil) • [Portfólio](https://github.com/seu-usuario)

---

## 📝 Licença

Este projeto é de propriedade da **Computex Sistemas Acadêmicos LTDA** e foi desenvolvido sob contrato freelancer.  
O código é público, mas este repositório serve apenas como **demonstração de portfólio profissional**.

---

> ⚙️ *"Automatizando processos e entregando precisão em leitura de gabaritos com integração inteligente."*
