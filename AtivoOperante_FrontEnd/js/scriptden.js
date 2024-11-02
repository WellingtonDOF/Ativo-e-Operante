let modal;
let backdrop;
let buttonClose;

function openModal(text) {

    console.log(text)
    modal.querySelector(".pModal").textContent = text;
    modal.classList.add("active");
    backdrop.classList.add("active");
    modal.show();
}
   // Função para fechar o modal
function closeModal() {
    modal.classList.remove("active");
    backdrop.classList.remove("active");
    modal.close();
}

 function initializeFeedback_Modal()
 {
    // PARTE DO MODAL 

    //const button = document.querySelector("button");
    modal = document.querySelector("dialog");
    backdrop = document.querySelector(".backdrop");
    buttonClose = document.querySelector("dialog button");

    // Função para abrir o modal
    

    // Abre o modal quando o botão é clicado
    //button.onclick = openModal;

    // Fecha o modal quando o botão "Fechar" é clicado
    buttonClose.onclick = closeModal;

    // Fecha o modal quando o clique é fora do modal
    backdrop.onclick = function(event) {
        event.stopPropagation();
    }

    // Impede que cliques dentro do modal sejam transmitidos para os elementos abaixo
    modal.onclick = function(event) {
        event.stopPropagation();
    };

    // Define o cursor como 'not-allowed' quando o mouse entra no backdrop
    backdrop.onmouseenter = function() {
        backdrop.style.cursor = 'not-allowed';
    };

    // FIM DA PARTE MODAL


    // Função para abrir ou fechar o feedback ao clicar na linha
    function toggleFeedback(index) {
        const clickedRow = document.querySelectorAll('tbody tr')[index];
        const feedbackRow = clickedRow.nextElementSibling;

        // Verifica se o próximo elemento é um feedback
        if (feedbackRow && feedbackRow.classList.contains('feedback')) {
            // Fecha todos os feedbacks abertos
            document.querySelectorAll('.feedback').forEach(function(row) {
                if (row.style.display !== 'none') {
                    // Adiciona um atraso ao fechar o feedback
                    setTimeout(function() {
                        row.style.display = 'none';
                        row.previousElementSibling.classList.remove('selected');
                        row.classList.remove('selected');
                    }, 300); // 500 milissegundos de atraso
                }
            });

            // Abre o feedback correspondente à linha clicada
            feedbackRow.style.display = feedbackRow.style.display === 'none' ? 'table-row' : 'none';
            clickedRow.classList.toggle('selected');
            feedbackRow.classList.toggle('selected');
        }
    }

    // Adicionar um ouvinte de evento a cada linha da tabela
    document.querySelectorAll('tbody tr').forEach(function(row, index) {
        row.addEventListener('click', function(event) {
            // Verifica se o alvo do clique não foi a imagem
            if (event.target.tagName.toLowerCase() !== 'img' && event.target.tagName.toLowerCase() !== 'button') {
                toggleFeedback(index);
            }
        });
    });

    // Função para abrir todos os feedbacks ao carregar a página
    window.addEventListener('DOMContentLoaded', function() {
        const allFeedbackRows = document.querySelectorAll('.feedback');
        allFeedbackRows.forEach(function(feedbackRow) {
            feedbackRow.style.display = 'none';
        });
    });

    window.addEventListener('DOMContentLoaded', function() {
        // Seleciona todos os elementos com a classe "tooltip"
        var tooltips = document.querySelectorAll('.tooltip');

        tooltips.forEach(function(tooltip) {
            tooltip.addEventListener('mouseenter', function() {
                // Seleciona o tooltip-text dentro do elemento pai do tooltip atual
                var tooltipText = tooltip.querySelector('.tooltip-text');

                // Calcula a altura do tooltip-text
                var tooltipHeight = tooltipText.offsetHeight;

                // Ajusta a posição do tooltip-text com base na sua altura
                tooltipText.style.top = '-' + (tooltipHeight + 10) + 'px';
            });
        });
    });
}

function excluirDen(id)
{
    const URL = "http://localhost:8080/apis/adm/delete-denuncia?id="+id;
    const divConclusion = document.getElementById("divConclusion");
    const token = localStorage.getItem('token');
    //var formU = document.getElementById("formU");
    var confirmacao = confirm("Tem certeza que deseja excluir?"); 

    console.log(id)

    if(confirmacao)
    {
        fetch(URL, {
            method: 'GET', 
            headers: {
                'Authorization': `Bearer ${token}`
            },
        })
        .then(resp => {

            if(resp.status===401)
                window.location.href="error.html";
            else
                if(resp.status===400)
                    openModal("Erro ao excluir, contate um administrador!");
                else
                {
                    divConclusion.style.display="block";
                    setTimeout(()=>{
                                location.reload();
                            },2000);
                }
           
        })
        .catch(error => {
            openModal("Erro ao excluir, contate um administrador!");
            console.error('Erro na requisição:', error);
        });
    }
    else
        openModal("Exclusão cancelada.");
}
