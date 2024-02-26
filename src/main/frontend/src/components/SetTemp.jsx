import React, { useEffect, useRef, useState } from "react";
import Slider from "rc-slider";
import styles from "./SetTemp.module.css";
import "rc-slider/assets/index.css";

export default function SetTemp({
  product,
  product: { name, min, max, initMin, initMax },
}) {
  const [temp, setTemp] = useState([Number(min), Number(max)]);
  const [disabled, setDisabled] = useState(false);
  const initTemp = useRef([Number(initMin), Number(initMax)]);

  const handleDisabled = () => {
    setDisabled((prev) => !prev);
  };

  useEffect(() => {
    product.min = temp[0];
    product.max = temp[1];
  }, [temp]);

  useEffect(() => {
    if (disabled) {
      product.min = 999;
      product.max = 999;
    } else {
      product.min = temp[0];
      product.max = temp[1];
    }
  }, [disabled]);

  return (
    <section className={styles.product}>
      <header className={styles.header}>
        <p
          className={
            disabled ? `${styles.name} ${styles.disabled}` : `${styles.name}`
          }
        >
          {name}
        </p>
        <p
          className={
            disabled
              ? `${styles.initTemp} ${styles.disabled}`
              : `${styles.initTemp}`
          }
        >
          ({initTemp.current[0]} ~ {initTemp.current[1]} ºF)
          <button
            className={styles.missing}
            onClick={handleDisabled}
            type='button'
          >
            결품
          </button>
        </p>
      </header>
      <article className={styles.slider}>
        <p
          className={
            disabled ? `${styles.temp} ${styles.disabled}` : `${styles.temp}`
          }
        >
          {disabled ? 999 : temp[0]} ºF
        </p>
        <Slider
          range
          onChange={(event) => setTemp([event[0], event[1]])}
          min={initTemp.current[0]}
          max={initTemp.current[1]}
          allowCross={false}
          disabled={disabled}
          defaultValue={[temp[0], temp[1]]}
        />

        <p
          className={
            disabled ? `${styles.temp} ${styles.disabled}` : `${styles.temp}`
          }
        >
          {disabled ? 999 : temp[1]} ºF
        </p>
      </article>
    </section>
  );
}
