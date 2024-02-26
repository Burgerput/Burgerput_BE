import React from "react";
import styles from "./Modal.module.css";
import Confirm from "./Confirm";
import { useModal } from "../hooks/useModal";

export default function Modal({ title, component, setResult, error, submit }) {
  const { close, confirm } = useModal({ setResult });

  return (
    <section className={styles.section}>
      <Confirm
        title={title}
        content={component}
        close={close}
        confirm={error ? submit : confirm}
      />
    </section>
  );
}
