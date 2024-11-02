
function setUser(event) {
    event.preventDefault();

    const URL = "http://localhost:8080/apis/security/get-usuario-email";
    var formUser = document.getElementById("formUser");
    var formData = new FormData(formUser);
    const divUser = document.getElementById("divUser");

    //Faz o objeto com os dados do form, e que bata de acordo com o do backend, nivel e ID não passa vai automatico no lado backend
    var userData = {
        cpf: formData.get('cpf'),
        email: formData.get('your_email'),
        senha: formData.get('password'),
        nome: formData.get('first_name')
    };

    fetch(URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData) //Envia os dados como json
    })
    .then(response => {

        divUser.style.display="block";
        if(response.status===400)
        {
            document.getElementById("pUser").textContent="Erro ao cadastrar, tente novamente."
            document.getElementById("pUser").style.color="red";

            setTimeout(()=>{
                divUser.style.display="none";
            },2000);
        }
        else
        {
            document.getElementById("pUser").textContent="Concluido, aguarde o redirecionamento."
            document.getElementById("pUser").style.color="green";

            setTimeout(()=>{
                window.location.href="index.html"
            },2000);
        }
    })
    .catch(error => {
        divUser.style.display="block";
        document.getElementById("pUser").textContent="Erro ao cadastrar, tente novamente."
        document.getElementById("pUser").style.color="red";
        setTimeout(()=>{
            divUser.style.display="none";
        },2000);
        console.error('Erro na requisição:', error);
    });
}


async function testeJWT(event) 
{
    event.preventDefault();

    const URL = "http://localhost:8080/apis/security/logar/";
    var formLogin = document.getElementById("formLogin");
    const senha = document.getElementById("pass");
    const divLogar = document.getElementById("logarDiv");
    const pUser = document.getElementById("pUser");

    try {

        const response = await fetch(URL, {
            method: 'POST', 
            body: new FormData(formLogin)
        });

        const json = await response.json();
        console.log(json)

        if(json.success === true) 
        {
            localStorage.setItem('token', json.token);

            if(json.usuario.nivel === 2) 
            {
                    divLogar.style.display = "block";
                    document.getElementById("pUser").textContent="Sucesso, aguarde o redirecionamento."
                    document.getElementById("pUser").style.color="green";       
                    setTimeout(() => {
                        divLogar.style.display = "none";
                        window.location.href="paginaUser.html";
                    }, 2200);
                    console.log("Parabéns, você logou como usuário");
            }
            else 
            {
                divLogar.style.display = "block";
                    document.getElementById("pUser").textContent="Sucesso, aguarde o redirecionamento."
                    document.getElementById("pUser").style.color="green";       
                    setTimeout(() => {
                        divLogar.style.display = "none";
                        window.location.href="paginaAdmin.html"; //pagina do adm
                    }, 2200);
                console.log("Parabéns, você logou como admin");
            }
            console.log(json.token + " funciona");
        } 
        else 
        {
            console.log(json.message)
            if(json.message === "CPF não encontrado") {
                divLogar.style.display = "block";
                document.getElementById("pUser").textContent="CPF não encontrado, tente novamente."
                document.getElementById("pUser").style.color="red";                
                setTimeout(() => {
                    divLogar.style.display = "none";
                }, 2200);
            } else if (json.message === "Senha incorreta") {
                divLogar.style.display = "block";
                document.getElementById("pUser").textContent="Senha incorreta, tente novamente."
                document.getElementById("pUser").style.color="red";      
                setTimeout(() => {
                    divLogar.style.display = "none";
                }, 2200);
            } 
        }
    }catch(error) {
        cpf.style.borderColor = "red";
        divLogar.style.display = "block";
        document.getElementById("pUser").textContent="Erro ao logar, tente novamente."
        document.getElementById("pUser").style.color="red";                
        setTimeout(() => {
            divLogar.style.display = "none";
        }, 2200);
        console.error('Erro na requisição:', error);
    }
}



function setDenuncia(event) {
    
    event.preventDefault();

    const URL = "http://localhost:8080/apis/cidadao/add-denuncia";
    var formDenuncia = document.getElementById("formDenuncia");
    const token = localStorage.getItem('token');

    console.log(formDenuncia)

    fetch(URL, {
        method: 'POST', 
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: new FormData(formDenuncia)
    })
    .then(resp => {

        if(resp.status===401)
            window.location.href="error.html";
        else
            if(resp.status===400)
                alert("Erro ao cadastrar.")
            else
            {
                setTimeout(() => {
                    location.reload();
                }, 2000);
            }
        //alert("Denuncia \u2022"+formDenuncia.elements["denName"].value+"\u2022 Concluida!");
    })
    .catch(error => {
        console.error('Erro na requisição:', error);
        //window.location.href="error.html";
    });
}


function setTipo(event) {
    
    event.preventDefault();

    const URL = "http://localhost:8080/apis/adm/add-tipo";
    var formTipo = document.getElementById("formTipo");
    var formData = new FormData(formTipo);
    const token = localStorage.getItem('token');

    var userData = {
        nome: formData.get('denTipo'),
    };
   
    fetch(URL, {
        method: 'POST',  
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(userData) // Converter para JSON
    })
    .then(resp => {
        //alert("Denuncia \u2022"+formDenuncia.elements["denName"].value+"\u2022 Concluida!");
        location.reload();
        return console.log(resp);
    })
    .catch(error => {
        console.error('Erro na requisição:', error);
    });
}


function setOrgao(event) {
    
    event.preventDefault();

    const URL = "http://localhost:8080/apis/adm/add-orgao";
    var formTipo = document.getElementById("formTipo");
    var formData = new FormData(formTipo);
    const token = localStorage.getItem('token');

    var userData = {
        nome: formData.get('denOrg'),
    };
   
    fetch(URL, {
        method: 'POST',  
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(userData) // Converter para JSON
    })
    .then(resp => {
        location.reload();
        return console.log(resp);
    })
    .catch(error => {
        console.error('Erro na requisição:', error);
    });
}


function carregaOrgaos()
{
    //Cancela o envio do form, evita o reload da página.
    //event.preventDefault();

    const URL="http://localhost:8080/apis/cidadao/get-all-orgaos";
    const tag = document.getElementById("orgaoCompetente");
    const token = localStorage.getItem('token');

    console.log(token)
    fetch(URL, {
        method: 'GET', 
        headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        return response.json(); // Processar os dados recebidos (assumindo que seja JSON)
    })
    .then(json => {
        let list=""
        
        json.forEach(element => {
            console.log(list);
            //Transforma a primeira letra em maiscula e concatena com o restante da palavra, a partir da posição 1 em diante.
            const nomeMaisculo = element.nome.charAt(0).toUpperCase() + element.nome.slice(1);
            list+=`<option value="${element.id}">${nomeMaisculo}</option>\n`;
        });

        tag.innerHTML = list;
        carregaTipoProblemas();
    })
    .catch(error => {
        console.error('Erro:', error);
    });
}


function carregaTipoProblemas() {
    const URL = "http://localhost:8080/apis/cidadao/get-all-problemas";
    const tag2 = document.getElementById("tipoProblema");
    const token = localStorage.getItem('token');

    fetch(URL, {
        method: 'GET', 
        headers: {
        'Content-Type': 'application/json', 
        'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        return response.json(); // Processar os dados recebidos (assumindo que seja JSON)
    })
    .then(json => {
        let list ="";
            
        json.forEach(element => {
            const nomeMaisculo = element.nome.charAt(0).toUpperCase() + element.nome.slice(1);
            list += `<option value="${element.id}">${nomeMaisculo}</option>\n`;
        });
        tag2.innerHTML=list;
    })
    .catch(error => {
        console.error('Erro:', error);
    });
} 


//Mudar o nome do arquivo na hora de escolher, tela de enviar denuncia..
function updateFileName(input) 
{
    //Coloca o nome do arquivo na caixa de span
    const fileName = input.files[0].name;
    document.getElementById('file_name').textContent = fileName;
}

async function carregaDenuncias() 
{
    //event.preventDefault();
    const URL = "http://localhost:8080/apis/adm/get-all-denuncias";
    const tag = document.getElementById("bodyTable");
    const token = localStorage.getItem('token');

    fetch(URL, {
        method: 'GET', 
        headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {

        if(response.status===401)
            window.location.href="error.html";
        else
            return response.json(); // Processar os dados recebidos (assumindo que seja JSON)
    })
    .then(async json => {
    
        let list ="";
        let link = "http://localhost:8080"
        let urgencia = "";
        let feedback = "";

        for(const element of json)
        {
     
            feedback = await obterFeedback(element.id);

            console.log(feedback)
            if(element.urgencia>=0 && element.urgencia<=2)
                urgencia="Baixa";
            else
                if(element.urgencia===3)
                    urgencia="Média";
                else
                    urgencia="Alta";

            if(element.texto===null)
                element.texto="-";

            list+=`
                <tr class="">
                    <td>${element.titulo}</td>
                    <td class="text-align: center;">
                        <button class="Modalbutton ver-texto" onclick="openModal('${element.texto}')">Ver texto</button>
                        <div class="backdrop">
                            <dialog>
                                <p class="pModal"></p>
                                <button class="Modalbutton">fechar</button>
                            </dialog>
                        </div>    
                    </td>
                    <td>${urgencia}</td>
                    <td>${element.orgId.nome}</td>
                    <td>${element.tipId.nome.charAt(0).toUpperCase()+element.tipId.nome.slice(1)}</td>
                    <td>${element.usId.nome}</td>
                    <td style="text-align: center;">
                        <a href="${link+element.imageName}" target="_blank">
                            <img src="${link+element.imageName}" alt="Imagem da Denúncia" width="50" height="50" onclick="event.stopPropagation()">
                        </a>
                    </td>
                    <td class="FeedbackMais">+</td>
                    <td class="text-align: center;">
                        <button class="Modalbutton" onclick="excluirDen('${element.id}')">Excluir</button>
                    </td>
                </tr>`;

            if(feedback.texto=='')
            {
                list+=`
                    <tr class="feedback" style="display: none;">
                        <td colspan="9">
                            <div style="margin-bottom: 10px;"> <!-- Adiciona uma margem inferior de 10px -->
                                <textarea id="feedback${element.id}" name="feedbackInput" cols="30" rows="3" class="form-control" placeholder="Digite o feedback" style="border: 2px solid #ccc;"></textarea>
                            </div>
                            <div> <!-- Cria um novo contêiner para os botões -->
                                <button style=" padding: 5px 10px;" class="Modalbutton" onclick="enviarFeedback('${element.id}')">Enviar</button>
                                <button style=" padding: 5px 10px;" class="Modalbutton" onclick="limparFeedbackInput('${element.id}')">Limpar</button>
                            </div>
                        </td>
                    </tr>`; 
            }
            else
            {
                list+=`
                    <tr class="feedback" style="display: none;">
                        <td colspan="9">
                            <p>${feedback.texto}</p>
                            <button style=" padding: 5px 10px;" class="Modalbutton" onclick="apagarFeedback('${element.id}')">Deletar</button>
                        </td>
                    </tr>`;
            }
        }
        console.log(json);
        tag.innerHTML=list;
        initializeFeedback_Modal();
    })
    .catch(error => {
        console.error('Erro:', error);
        window.location.href="error.html"
    });
} 


async function obterFeedback(id) {
    URL2 = "http://localhost:8080/apis/adm/get-feedback?id=" + id;
    const token = localStorage.getItem('token');

    const response = await fetch(URL2, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        },
    });

    const json = await response.json(); // Processar os dados recebidos (assumindo que seja JSON)

    return json; // Retorna os dados para serem usados em 'carregaDenuncias'
}


function enviarFeedback(id) 
{
    const texto = document.getElementById("feedback" + id).value;
    const URL = "http://localhost:8080/apis/adm/add-feedback";
    const formData = new FormData();
    formData.append('id', id);
    formData.append('feedback', texto);
    const token = localStorage.getItem('token');

    fetch(URL, {
        method: 'POST',
        headers: {
        'Authorization': `Bearer ${token}`
        },
        body: formData
    })
    .then(resp =>{
        if(resp.status===401)
            window.location.href="error.html";
        else
            location.reload();
    })
    .then(json => {
        console.log(json);
    })
    .catch(error => {
        console.error('Erro ', error);
    });
}


function apagarFeedback(id) 
{
    const URL = "http://localhost:8080/apis/adm/delete-feedback";
    const formData = new FormData();
    const token = localStorage.getItem('token');

    formData.append('id', id);

    const confirmacao = confirm("Tem certeza que deseja excluir o Feedback?");
    
    if(confirmacao) 
    {
        fetch(URL, {
            method: 'POST',    
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: formData
        })
        .then(resp =>{
            if(resp.status===401)
                window.location.href="error.html";
            else
                location.reload();
        }) 
        .then(json => {
            console.log(json);
        })
        .catch(error => {
            console.error('Erro ', error);
        });
    }
    //else 
        //alert("Exclusão cancelada!");
}

function limparFeedbackInput(id) {

    const textarea = document.getElementById("feedback"+id);
    textarea.value='';
}

