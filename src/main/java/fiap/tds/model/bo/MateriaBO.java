package fiap.tds.model.bo;

import fiap.tds.model.vo.Materia;

public class MateriaBO {
    public boolean materiaValida(Materia materia) {
        return isCamposObrigatoriosPreenchidos(materia) && isStatusValido(materia) && isDatasValidas(materia);
    }

    private boolean isCamposObrigatoriosPreenchidos(Materia materia) {
        return materia.getIdUsuario() > 0 &&
            materia.getTituloMateria() != null && !materia.getTituloMateria().trim().isEmpty() &&
            materia.getTextoMateria() != null && !materia.getTextoMateria().trim().isEmpty();
    }

    private boolean isStatusValido(Materia materia) {
        return materia.isAtivo() != null && (materia.isAtivo().equalsIgnoreCase("S") || materia.isAtivo().equalsIgnoreCase("N"));
    }

    private boolean isDatasValidas(Materia materia) {
        if (materia.getDtCriacao() == null || materia.getDtUpdate() == null) {
            return false;
        }
        return !materia.getDtUpdate().isBefore(materia.getDtCriacao());
    }
}
