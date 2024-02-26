import React, { useEffect } from "react";
import styles from "./CustomProducts.module.css";
import { customProductsHook } from "../hooks/useCustomProducts";

export default function CustomProducts({
  value: { name, min, max, id },
  setProducts,
  checkedIt,
}) {
  const { checked, handleChange } = customProductsHook({
    setProducts,
    id,
    min,
    max,
    checkedIt,
  });

  useEffect(() => {
    if (checked) {
      setProducts((prev) => [...prev, { id, min, max, checked }]);
    }
  }, []);

  return (
    <div className={styles.product}>
      <input
        className={styles.check}
        type='checkbox'
        id={id}
        checked={checked}
        onChange={handleChange}
      />
      <label className={styles.text} htmlFor={id}>
        {name}
        <span className={styles.text__temp}>
          ({min} ~ {max} ºF)
        </span>
      </label>
    </div>
  );
}
