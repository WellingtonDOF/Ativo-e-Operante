

function testeJWT(event)
{
    //event.preventDefault();

    const URL = "http://localhost:8080/apis/adm/teste-conexao";
    const form = document.getElementById("loginForm");

    fetch(URL, {
        method: 'POST',
        body: new FormData(form)
    })
    .then(resp => {
        return resp.text();
    })
    .then(data =>{
        console.log(data);
        localStorage.setItem('token',data);
    })
    .catch(error => {
        console.error('Erro na requisição:', error);
    });
}


function testeJW(event)
{
    //event.preventDefault();

    const URL = "http://localhost:8080/apis/cidadao/teste";
    const token = localStorage.getItem('token');
    const form = document.getElementById("loginForm");

    console.log(token)
    fetch(URL, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`, // Adiciona o token ao cabeçalho de autorização
        },
        body: new FormData(form)
    })
    .then(resp => {
        return resp.text();
    })
    .then(data =>{
        console.log(data);
    })
    .catch(error => {
        console.error('Erro na requisição:', error);
    });
}

