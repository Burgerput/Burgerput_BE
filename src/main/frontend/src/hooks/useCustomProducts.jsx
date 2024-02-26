import { useState } from "react";

export function customProductsHook({ setProducts, id, checkedIt, min, max }) {
  const [checked, setChecked] = useState(checkedIt);
  const handleChange = (e) => {
    setChecked((prev) => !prev);
    handleCheck(e.target.checked);
  };
  const deleteProducts = (id) => {
    setProducts((prev) => prev.filter((product) => product.id !== id));
  };
  const handleCheck = (isChecked) => {
    if (isChecked) {
      setProducts((prev) => [...prev, { id, min, max, isChecked }]);
    } else {
      deleteProducts(id);
    }
  };

  return { checked, handleChange };
}
