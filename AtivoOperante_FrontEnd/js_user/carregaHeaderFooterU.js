function carregaHeaderFooterU() {
    const URL = "http://localhost:8080/apis/user/get-parametrizacao";
    const token = localStorage.getItem('jwtToken');
    const tag = document.getElementById("headerUser");
    const tag2 = document.getElementById("footerUser");
    var link = "http://localhost:8080";

       
        // Construção do conteúdo dinâmico com base nos dados recebidos
        let headerContent = `<div class="logo">
                                <img src="" alt="Logo "> 
                            </div>
                            <nav class="main-nav">
                                <ul>
                                    <li><a href="paginaUser.html">Home</a></li>
                                    <li><a href="enviarDenuncia.html">Enviar Denúncia</a></li>
                                    <li></li>
                                    <li></li>
                                    <li><a href="index.html" onclick="removerToken()">Sair</a></li>
                                </ul>
                            </nav>
                            <div class="menu-toggle">
                                <input type="checkbox" id="toggle">
                                <label for="toggle" class="toggle-btn">
                                    <span></span>
                                    <span></span>
                                    <span></span>
                                </label>
                                <div class="menu">
                                    <ul>
                                        <li><a href="paginaUser.html">Home</a></li>
                                        <li><a href="enviarDenuncia.html">Enviar Denúncia</a></li>
                                        <li><a href="index.html" onclick="removerToken()">Sair</a></li>
                                    </ul>
                                </div>
                            </div>`;
        tag.innerHTML = headerContent;

        let footerContent = `<div class="footer-content">
                                <div class="footer-section about">
                                    <h3>Sobre Nós</h3>
                                    <p>Cidade Ativa, planejando o seu e o nosso futuro.</p>
                                </div>                  
                                <div class="footer-section links">
                                    <h3>Links Úteis</h3>
                                    <ul>
                                    <li><a href="paginaUser.html">Home</a></li>
                                    <li><a href="enviarDenuncia.html">Enviar Denúncia</a></li>
                                    </ul>
                                </div>
                                <div class="footer-section contact">
                                    <h3>Contato</h3>
                                    <p>Email: </p>
                                    <p>Telefone: (99) 99999-9999</p>
                                </div>
                                <div class="footer-section social">
                                    <h3>Redes Sociais</h3>
                                    <div class="social-icons">
                                        <a href="#"><img src="https://www.facebook.com/images/fb_icon_325x325.png" alt="Facebook"></a>
                                        <a href="#"><img src="https://img.freepik.com/vetores-premium/novo-logotipo-do-twitter-x-2023-download-do-vetor-do-logotipo-do-twitter-x_691560-10794.jpg" alt="Twitter"></a>
                                        <a href="#"><img src="https://www.instagram.com/static/images/ico/favicon-192.png/68d99ba29cc8.png" alt="Instagram"></a>
                                    </div>
                                </div>
                            </div>

                            <div class="footer-bottom">
                                <p>&copy; AtivoOperante 2024. Todos os direitos reservados.</p>
                            </div>`;

        tag2.innerHTML = footerContent;
}

