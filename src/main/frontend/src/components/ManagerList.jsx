import React, { useEffect } from "react";
import CreatableSelect from "react-select/creatable";
import styles from "./ManagerList.module.css";
import { useManagerList } from "../hooks/useManagerList";

export default function ManagerList({
  mgrList,
  setSelectManager,
  selectManager,
}) {
  const { options, customStyles, handleChange } = useManagerList({
    mgrList,
    setSelectManager,
  });

  useEffect(() => {
    setSelectManager(selectManager);
  }, [selectManager]);

  return (
    <div className={styles.box}>
      <CreatableSelect
        className={styles.select}
        placeholder={selectManager === null ? "입력" : "관리자 선택"}
        options={[...options, { value: "직접입력", label: "직접 입력" }]}
        onChange={handleChange}
        value={selectManager}
        styles={customStyles}
        formatCreateLabel={(inputValue) => inputValue}
      />
    </div>
  );
}
