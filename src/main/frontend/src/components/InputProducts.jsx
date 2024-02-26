import React, { useEffect } from "react";
import styles from "./InputProducts.module.css";
import Banner from "./Banner";
import { useInputProducts } from "../hooks/useInputProducts";

export default function InputProducts({
  product: { name, min, max },
  product,
}) {
  const { temp, warning, handleChange, handleClick, missing, inputContainer } =
    useInputProducts({ min, max });

  useEffect(() => {
    product.temp = temp;
  }, [product, temp]);

  return (
    <div className={styles.product} ref={inputContainer}>
      <div className={styles.text}>
        <p className={styles.text__title}>{name} :</p>
        <p className={styles.text__temp}>
          ({min} ~ {max}ºF)
        </p>
      </div>
      <div className={styles.container}>
        <div className={styles.inputBox}>
          {warning && <small className={styles.warning}>다시 입력</small>}
          <button
            className={styles.missing}
            onClick={handleClick}
            type='button'
            tabIndex='-1'
          >
            결품
          </button>
          <input
            className={missing ? `${styles.input__missing}` : `${styles.input}`}
            type='number'
            value={temp}
            disabled={missing}
            onChange={handleChange}
          />
          <p
            className={
              missing ? `${styles.text__temp__missing}` : `${styles.text__temp}`
            }
          >
            ºF
          </p>
        </div>
      </div>
    </div>
  );
}
