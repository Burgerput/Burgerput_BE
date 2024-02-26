import styles from "./SelectManagers.module.css";
import { PiTrashBold } from "react-icons/pi";
import { RiArrowGoBackFill } from "react-icons/ri";
import { AiOutlineEnter } from "react-icons/ai";
import { useManagers } from "./../hooks/useManagers";
import { useGoHome } from "../hooks/useNavigator";

export default function SelectManagers() {
  const { handleClick } = useGoHome();

  const {
    managersQuery: { isLoading, error, data: initialManagers },
    manager,
    handleChange,
    handleSubmit,
    handleDelete,
  } = useManagers();

  return (
    <>
      {isLoading && <div>Loading...</div>}
      {error && <div>{error}</div>}
      {initialManagers && (
        <section className={styles.section}>
          <div className={styles.header}>
            <div className={styles.title}>관리자 목록 수정</div>
            <RiArrowGoBackFill
              className={styles.header__button}
              onClick={handleClick}
            />
          </div>

          <ul className={styles.box}>
            {initialManagers &&
              initialManagers.map(({ id, mgrname }) => (
                <li className={styles.card} key={id}>
                  <span className={styles.card__name}>{mgrname}</span>
                  <button
                    onClick={() => {
                      handleDelete(id, mgrname);
                    }}
                    className={styles.cover}
                  >
                    <PiTrashBold />
                  </button>
                </li>
              ))}
          </ul>
          <form className={styles.form} onSubmit={handleSubmit}>
            <input
              className={styles.input}
              type='text'
              value={manager}
              onChange={handleChange}
            />
            <button className={styles.button}>
              <AiOutlineEnter />
            </button>
          </form>
        </section>
      )}
    </>
  );
}
