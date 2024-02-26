import React, { useState } from "react";
import { Link } from "react-router-dom";
import styles from "./Navbar.module.css";
import { FaGithub, FaTwitter, FaInstagram } from "react-icons/fa";

export default function Navbar({ setVisible, setHidden, cheat }) {
  const [dropDown, setDropDown] = useState("");
  const handleDropDownVisibility = (index) => {
    setDropDown(index);
  };

  const handleDropDownHidden = () => {
    setVisible();
    setDropDown("");
  };
  return (
    <nav className={styles.navbar}>
      <section className={styles.navbar__wrapper}>
        <section className={styles.navbar__depth1}>
          <Link to='/'>
            <button
              onClick={() => {
                setHidden();
                setDropDown("");
              }}
              className={styles.menu}
            >
              &nbsp;&nbsp;Main
            </button>
          </Link>
          <button
            onClick={() => {
              if (dropDown === "1") {
                setDropDown("");
                return;
              }
              handleDropDownVisibility("1");
            }}
            className={styles.menu}
          >
            &nbsp;&nbsp;Admins
          </button>
          <button
            onClick={() => {
              if (dropDown === "2") {
                setDropDown("");
                return;
              }
              handleDropDownVisibility("2");
            }}
            className={styles.menu}
          >
            &nbsp;&nbsp;Zenput
          </button>
          <button
            onClick={() => {
              if (dropDown === "3") {
                setDropDown("");
                return;
              }
              handleDropDownVisibility("3");
            }}
            className={styles.menu}
          >
            &nbsp;&nbsp;Contact
          </button>
          {cheat && (
            <button
              onClick={() => {
                if (dropDown === "cheat") {
                  setDropDown("");
                  return;
                }
                handleDropDownVisibility("cheat");
              }}
              className={styles.menu}
            >
              &nbsp;&nbsp;Cheat
            </button>
          )}
        </section>
        {dropDown === "cheat" && (
          <section className={styles.navbar__depth2}>
            <div className={styles.menu__depth2__contact__hidden}>hidden</div>
            <div className={styles.menu__depth2__contact__hidden}>hidden</div>
            <Link to='/cheat/help'>
              <button
                className={styles.menu__depth2}
                onClick={handleDropDownHidden}
              >
                사용 방법
              </button>
            </Link>
            <Link to='/cheat/machine'>
              <button
                className={styles.menu__depth2}
                onClick={handleDropDownHidden}
              >
                기기 범위 지정
              </button>
            </Link>
            <Link to='/cheat/food'>
              <button
                className={styles.menu__depth2}
                onClick={handleDropDownHidden}
              >
                식품 범위 지정
              </button>
            </Link>
          </section>
        )}
        {dropDown === "1" && (
          <section className={styles.navbar__depth2}>
            <Link to='select/machines'>
              <button
                onClick={handleDropDownHidden}
                className={styles.menu__depth2}
              >
                기기 선택
              </button>
            </Link>
            <Link to='select/foods'>
              <button
                onClick={handleDropDownHidden}
                className={styles.menu__depth2}
              >
                식품 선택
              </button>
            </Link>
            <Link to='select/managers'>
              <button
                onClick={handleDropDownHidden}
                className={styles.menu__depth2}
              >
                관리자 목록 수정
              </button>
            </Link>
            <Link to='address'>
              <button
                onClick={handleDropDownHidden}
                className={styles.menu__depth2}
              >
                관리자 계정 추가
              </button>
            </Link>
          </section>
        )}
        {dropDown === "2" && (
          <section className={styles.navbar__depth2}>
            <Link to='/zenput/machines' state={"AM"}>
              <button
                className={styles.menu__depth2}
                onClick={handleDropDownHidden}
              >
                오전 기기
              </button>
            </Link>
            <Link to='/zenput/foods' state={"AM"}>
              <button
                className={styles.menu__depth2}
                onClick={handleDropDownHidden}
              >
                오전 식품
              </button>
            </Link>
            <Link to='/zenput/machines' state={"PM"}>
              <button
                className={styles.menu__depth2}
                onClick={handleDropDownHidden}
              >
                오후 기기
              </button>
            </Link>
            <Link to='/zenput/foods' state={"PM"}>
              <button
                className={styles.menu__depth2}
                onClick={handleDropDownHidden}
              >
                오후 식품
              </button>
            </Link>
          </section>
        )}
        {dropDown === "3" && (
          <section className={styles.navbar__depth2__contact}>
            <div className={styles.menu__depth2__contact__hidden}>hidden</div>
            <div className={styles.menu__depth2__contact__hidden}>hidden</div>
            <div className={styles.menu__depth2__contact}>
              <div className={styles.nameBox}>
                <p className={styles.name}>Frolip</p>
                <p className={styles.job}>Front-End Engineer</p>
              </div>
              <div className={styles.link}>
                <p className={styles.link__icon__git}>
                  <FaGithub />
                </p>
                <p className={styles.link__icon__twi}>
                  <FaTwitter />
                </p>
                <p className={styles.link__icon__ins}>
                  <FaInstagram />
                </p>
              </div>
            </div>
            <div className={styles.menu__depth2__contact}>
              <div className={styles.nameBox}>
                <p className={styles.name}>Yellta</p>
                <p className={styles.job}>Back-End Engineer</p>
              </div>
              <div className={styles.link}>
                <p className={styles.link__icon__git}>
                  <FaGithub />
                </p>
                <p className={styles.link__icon__twi}>
                  <FaTwitter />
                </p>
                <p className={styles.link__icon__ins}>
                  <FaInstagram />
                </p>
              </div>
            </div>
          </section>
        )}
      </section>
    </nav>
  );
}
