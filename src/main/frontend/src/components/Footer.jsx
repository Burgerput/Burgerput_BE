import React from "react";
import styles from "./Footer.module.css";

export default function Footer() {
  return (
    <footer className={styles.footer}>
      <div className={styles.contacts}>
        {/* <div className={styles.contact}>
          <p>Etart</p>
          <p>Link</p>
        </div>
        <div className={styles.contact}>
          <p>Frolip</p>
          <p>Link</p>
        </div> */}
      </div>
      <p className={styles.copyright}>
        Copyright 2023. Etart, Frolip All Rights Reserved
      </p>
    </footer>
  );
}
