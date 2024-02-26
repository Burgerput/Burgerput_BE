import React from "react";
import styles from "./Banner.module.css";

export default function Banner({ text, type }) {
  return (
    <section className={type === "loading" ? `${styles.loading_overlay}` : ""}>
      <div
        className={
          type === "loading" ? `${styles.loading}` : `${styles.banner}`
        }
      >
        {text}
      </div>
    </section>
  );
}
