import React from "react";
import CustomProducts from "./../components/CustomProducts";
import styles from "./CustomMachines.module.css";
import { useMachines } from "./../hooks/useProducts";
import Banner from "./../components/Banner";
import Button from "../components/Button";
import { useGoHome } from "../hooks/useNavigator";

export default function InputMachines() {
  const { handleClick } = useGoHome();

  const {
    productsQuery: { isLoading, error, data: machines },
    handleSubmit,
    setProducts,
    success,
  } = useMachines();

  return (
    <>
      {isLoading && <p>Loading...</p>}
      {error && <p>{error}</p>}
      {machines && (
        <section className={styles.section}>
          <div className={styles.title}>기기 선택</div>
          <div className={styles.wrapper}>
            <form
              id='customMachine'
              className={styles.form}
              onSubmit={handleSubmit}
            >
              {success && <Banner text={"기기 선택이 완료되었습니다."} />}
              <div className={styles.products}>
                {machines &&
                  machines.map((machine) => (
                    <div key={machine.id}>
                      <CustomProducts
                        value={machine}
                        setProducts={setProducts}
                        checkedIt={machine.isChecked === "true" ? true : false}
                      />
                    </div>
                  ))}
              </div>
            </form>
            <div className={styles.buttons}>
              <Button text={"저장"} type={"submit"} form={"customMachine"} />
              <Button text={"취소"} handleFunction={handleClick} />
            </div>
          </div>
        </section>
      )}
    </>
  );
}
