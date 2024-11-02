function removerToken()
{
   localStorage.removeItem('token');
}

function tokenVerify()
{
    const URL = "http://localhost:8080/apis/security/token-Supremo";
    const token = localStorage.getItem('token');

    fetch(URL, {
        method: 'POST', 
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        if(response.status===401)
            window.location.href="error.html";
        else
            return response.json; // Processar os dados recebidos (assumindo que seja JSON)
    })
    .catch(error => {
        console.error('Erro:', error);
    });
}

function verificaNivel()
{
    const URL = "http://localhost:8080/apis/adm/verifica-nivel";
    const token = localStorage.getItem('token');
    fetch(URL, {
        method: 'POST', 
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        if(response.status===401)
            window.location.href="error.html";
        return response.json; // Processar os dados recebidos (assumindo que seja JSON)
    })
    .catch(error => {
        console.error('Erro:', error);
    });
}