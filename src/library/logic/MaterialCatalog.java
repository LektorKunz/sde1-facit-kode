package library.logic;

import java.util.ArrayList;
import java.util.List;

public class MaterialCatalog {
    private List<Material> materials = new ArrayList<>();

    public void add(Material material) {
        materials.add(material);
    }

    public Material findByTitle(String title) {
        for (Material m : materials) {
            if (m.getTitle().equals(title)) {
                return m;
            }
        }
        return null;
    }

    public List<Material> getAll() {
        return materials;
    }
}
