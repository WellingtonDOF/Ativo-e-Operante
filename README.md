## Ativo e Operante

Esse projeto é um sistema web capaz de oferecer uma ferramenta ao usuário que deseja avisar ou denunciar algum tipo de problema aos orgãos competentes da cidade.

Conta com autenticação atráves de JWT onde é gerado um token de acesso, assim, permitindo que o usuário utilize o sistema por um tempo definido.

Possui módulo de denúncia, no qual o usuário consegue registrar uma denuncía informando, título, descrição, urgência do problema, selecionar o orgão competente 
e o tipo do problema, além de poder adicionar uma imagem; orgão competente e tipo de problema são carregados a partir de um serviço web no formato JSON com Spring REST.

Conta com outro módulo de registro de usuário, onde é fornecido cpf, email e senha e então é enviado para uma API que faz toda a verificação e registra o usuario.

Também conta com um módulo, onde o responsável pelo sistema pode cadastrar os orgãos e os tipos de problemas, além de possuir uma área que lista as denúncias, com
opções para deletar/ e ou dar um feedback.

Ao logar o sistema verifica qual o nível do usuário e redireciona-o de acordo com o seu tipo, user ou admin.

Todos os dados cadastrados são persistidos no banco de dados.
