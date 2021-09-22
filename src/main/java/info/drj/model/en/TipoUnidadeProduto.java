/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.model.en;

/**
 *
 * @author Dirceu Junior
 */
public enum TipoUnidadeProduto {

    UNITARIO("Unitário"),
    QUILOGRAMA("Quilograma"),
    LITRO("Litro"),
    METRO("Metro"),
    METRO_QUADRADO("Metro²"),
    METRO_CUBICO("Metro³");

    private String descricao;

    TipoUnidadeProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
