import React from "react";
import styles from "./Help.module.css";

export default function Help() {
  return (
    <section className={styles.help}>
      <header className={styles.title}>Cheat Mode</header>
      <section className={styles.main}>
        <article className={styles.tab}>
          <h1>진입 방법</h1>
          <p>■ 해당 모드 진입은 메인 로고 이미지 3번 클릭입니다.</p>
        </article>
        <article className={styles.tab}>
          <h1>사용 방법</h1>
          <div>
            <p>■ 범위 지정 탭을 클릭합니다.</p>
            <p>
              ■ 각 식품, 기기별 온도의 최소, 최대값을 사용자에게 제공합니다.
            </p>
            <p>■ 사용자는 최소, 최대값을 원하는 범위로 지정합니다.</p>
            <p>
              ■ 지정된 범위 내에서 랜덤한 값을 Zenput에 입력하고 제출합니다.
            </p>
          </div>
        </article>
        <article className={styles.tab}>
          <h1>주의사항</h1>
          <div>
            <p>■ 한번 지정된 범위는 시스템에 저장됩니다.</p>
            <p>■ 제품 선택시 저장된 범위는 초기화 됩니다.</p>
            <p>■ 저장된 범위는 계속 사용하거나 재조정이 가능합니다.</p>
            <p>■ 해당 모드 사용 중 적발시 개발자는 책임지지 않습니다.</p>
          </div>
        </article>
      </section>
    </section>
  );
}
