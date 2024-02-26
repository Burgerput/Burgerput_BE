import React from "react";
import styles from "./Button.module.css";

export default function Button({ text, handleFunction, type, form, disabled }) {
  return (
    <button
      className={styles.button}
      type={type && type}
      form={form && form}
      onClick={handleFunction && handleFunction}
      disabled={disabled ? true : false}
    >
      {text}
    </button>
  );
}
