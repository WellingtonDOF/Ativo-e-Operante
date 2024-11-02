package br.unoeste.wellington.ativooperante2024.db.entities;


import jakarta.persistence.*;

@Entity
@Table(name="feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fee_id")
    private Long id;

    @Column(name="fee_texto")
    private String texto;


    // NÃO COLOCAR ESSA COLUNA NO CONSTRUCTOR NEM NO GET/SET, POIS SERVE APENAS PARA RECUPERAÇÃO, CASO COLOQUE
    // VAI DAR PROBLEMA DE RECURSIVIDADE...
    @OneToOne(fetch = FetchType.LAZY)// Para pegar de forma automatica o id da denuncia, com carregamento posterior(TARDIO)...
    @JoinColumn(name="den_id")
    private Denuncia denuncia;

    public Feedback()
    {
        this(0L,"");
    }

    public Feedback(Long id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDenunciaID(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
