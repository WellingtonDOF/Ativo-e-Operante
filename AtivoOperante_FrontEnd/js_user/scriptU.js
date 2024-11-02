
async function carregaDenuncias() 
{
    //event.preventDefault();
    const URL = "http://localhost:8080/apis/cidadao/get-all-denuncias";
    const tag = document.getElementById("bodyTable");

    const token = localStorage.getItem('token');
    
    console.log(token)
    fetch(URL, {
        method: 'GET', 
        headers: {
        'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {

        console.log("entrou")
        if(response.status===401)
            window.location.href="error.html"
        
        return response.json(); // Processar os dados recebidos (assumindo que seja JSON)
    })
    .then(async json => {
        
        let list ="";
        let link = "http://localhost:8080"
        let urgencia = "";
        let feedback = "";
        console.log("aaa "+json);
        
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
                </tr>`;

            if(feedback.texto=='')
            {
                list+=`
                <tr class="feedback" style="display: none;">
                    <td colspan="9">
                        <p>-</p>
                    </td>
                </tr>`;
            }
            else
            {
                list+=`
                    <tr class="feedback" style="display: none;">
                        <td colspan="9">
                            <p>${feedback.texto}</p>
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
        //window.location.href="error.html"
    });
} 


async function obterFeedback(id) {
    const URL = "http://localhost:8080/apis/adm/get-feedback?id=" + id;

    const response = await fetch(URL, {
        method: 'GET'
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

    fetch(URL, {
        method: 'POST',
        body: formData
    })
    .then(resp => resp.json())
    .then(json => {
        console.log(json);
    })
    .catch(error => {
        console.error('Erro ', error);
    });
}

function limparFeedbackInput() {
    document.getElementById("feedbackInput").value = "";
}