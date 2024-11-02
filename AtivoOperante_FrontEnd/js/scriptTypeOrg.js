function carregaTipo() 
{
    //event.preventDefault();
    const URL = "http://localhost:8080/apis/adm/get-all-tipos";
    const tag = document.getElementById("bodyTable");

    fetch(URL, {
        method: 'GET', 
        headers: {
        'Content-Type': 'application/json' 
        }
    })
    .then(response => {

        return response.json(); // Processar os dados recebidos (assumindo que seja JSON)
    })
    .then(json=>{
        console.log(json);

        let list ="";

        for(const element of json)
        {
            list+=`
                <tr class="">
                    <td>${element.nome}</td>
                    <td class="text-align: center;">
                        <button class="Modalbutton" onclick="abrirModal('${element.id}','${element.nome}')">Alterar</button>
                        <button class="Modalbutton" onclick="excluirTipo('${element.id}')">Excluir</button>
                    </td>
                </tr>`;
        }
        tag.innerHTML=list;
        console.log(json);
    })
    .catch(error => {
        console.error('Erro:', error);
    });
} 



function carregaOrgao() 
{
    //event.preventDefault();
    const URL = "http://localhost:8080/apis/adm/get-all-orgaos";
    const tag = document.getElementById("bodyTable");

    fetch(URL, {
        method: 'GET', 
        headers: {
        'Content-Type': 'application/json' 
        }
    })
    .then(response => {

        return response.json(); // Processar os dados recebidos (assumindo que seja JSON)
    })
    .then(json=>{
        console.log(json);

        let list ="";

        for(const element of json)
        {
            list+=`
                <tr class="">
                    <td>${element.nome}</td>
                    <td class="text-align: center;">
                        <button class="Modalbutton" onclick="abrirModal('${element.id}','${element.nome}')">Alterar</button>
                        <button class="Modalbutton" onclick="excluirOrgao('${element.id}')">Excluir</button>
                    </td>
                </tr>`;
        }
        tag.innerHTML=list;
        console.log(json);
    })
    .catch(error => {
        console.error('Erro:', error);
    });
} 


function alterarTipo(id, texto)
{
    const URL = "http://localhost:8080/apis/adm/alterar-tipo";
    const formData = new FormData();
    const token = localStorage.getItem('token');

    formData.append('id', id);
    formData.append('nome',texto);

    fetch(URL, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
        body: formData
    })
    .then(resp =>{
        
        if(resp.status===401)
            window.location.href="error.html";
        return resp.json;
    })
    .then(json => {
        carregaDenuncias();
        console.log(json);
    })
    .catch(error => {
        console.error('Erro ', error);
    });

}


function alterarOrgao(id, texto)
{
    const URL = "http://localhost:8080/apis/adm/alterar-orgao";
    const formData = new FormData();
    const token = localStorage.getItem('token');

    formData.append('id', id);
    formData.append('nome',texto);

    fetch(URL, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
        body: formData
    })
    .then(resp =>{
        if(resp.status===401)
            window.location.href="error.html";
        return resp.json;
    })
    .then(json => {
        carregaDenuncias();
        console.log(json);
    })
    .catch(error => {
        console.error('Erro ', error);
    });
}

function excluirTipo(id)
{
    console.log("id para excluir "+id)
    const URL = "http://localhost:8080/apis/adm/delete-tipo";
    const token = localStorage.getItem('token');
    const formData = new FormData();
    formData.append('id', id);

    const confirmacao = confirm("Tem certeza que deseja excluir o tipo?");
    const divTipo = document.getElementById("divTipo");
    if(confirmacao) 
    {
        fetch(URL, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
            body: formData
        })
        .then(resp =>{

            if(resp.status===401)
                window.location.href="error.html";
            else
                if(resp.status===400)
                {
                    divTipo.style.display="block";
                    setTimeout(() => {
                        divTipo.style.display="none";
                    }, 2200);
                }
            return resp.json;
        })
        .then(json => {
            setTimeout(() => {
                location.reload();
            }, 2200);
            console.log(json);
        })
        .catch(error => {
            console.error('Erro ', error);
        });
    }
    //else 
        //alert("Exclusão cancelada!");
}


function excluirOrgao(id)
{
    console.log("id para excluir "+id)
    const URL = "http://localhost:8080/apis/adm/delete-orgao";
    const token = localStorage.getItem('token');
    const divTipo = document.getElementById("divTipo");

    const formData = new FormData();
    formData.append('id', id);

    const confirmacao = confirm("Tem certeza que deseja excluir o tipo?");
    
    if(confirmacao) 
    {
        fetch(URL, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
            body: formData
        })
        .then(resp =>{

            if(resp.status===401)
                window.location.href="error.html";
            else
                if(resp.status===400)
                {
                    divTipo.style.display="block";
                    setTimeout(() => {
                        divTipo.style.display="none";
                    }, 2200);
                }
            return resp.json;
        })
        .then(json => {

            setTimeout(() => {
                location.reload();
            }, 2200);
            console.log(json);
        })
        .catch(error => {
            console.error('Erro ', error);
        });
    }
    //else 
        //alert("Exclusão cancelada!");
}

var idG;
var nomeG;

function abrirModal(id,nome) {
    idG=id;
    nomeG=nome;
    console.log(id, nome)
    const modal = document.getElementById('meuModal');
    modal.style.display = 'block'; // Exibe o modal
  }
  
function fecharModal() {
    const modal = document.getElementById('meuModal');
    modal.style.display = 'none'; // Oculta o modal
}

function enviarMudanca(ident) {
    var texto = document.getElementById('texto').value;

    console.log("dentro "+idG, nomeG)
    if(texto==="")
        texto=nomeG;

    if(ident=='T')
        alterarTipo(idG, texto);
    else
        alterarOrgao(idG, texto);

    fecharModal(); // Fecha o modal após enviar
    location.reload();
}
