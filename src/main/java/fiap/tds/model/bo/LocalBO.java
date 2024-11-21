package fiap.tds.model.bo;

import fiap.tds.model.vo.Local;

public class LocalBO {

    public boolean isValido(Local local) {
        return isCamposObrigatoriosPreenchidos(local);
    }

    private boolean isCamposObrigatoriosPreenchidos(Local local) {
        return local.getNome() != null && !local.getNome().trim().isEmpty() &&
                local.getCategoria() != null && !local.getCategoria().trim().isEmpty() &&
                local.getLogradouro() != null && !local.getLogradouro().trim().isEmpty() &&
                local.getCep() != null && !local.getCep().trim().isEmpty() &&
                local.getCidade() != null && !local.getCidade().trim().isEmpty() &&
                local.getEstado() != null && !local.getEstado().trim().isEmpty();
    }
}
