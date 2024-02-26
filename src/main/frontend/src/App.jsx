import { Outlet } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import styles from "./App.module.css";
import TypeIt from "typeit-react";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import { useDisplay } from "./hooks/useOutletDisplay";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false, // window focus 설정
    },
  },
});

function App() {
  const {
    handleHidden,
    handleVisible,
    handleCount,
    isOutletHidden,
    hiddenCount,
  } = useDisplay();

  // 날짜 검사 후 로딩하는 로직을 서버에서 크론탭을 이용해 매일 오전 8시에 수행하기 때문에 더 이상 실행하지 않음
  // const { checkDate, reLoad, setResult, result, loading } = useDateCheck();

  // useEffect(() => {
  //   checkDate();
  // }, []);

  return (
    <QueryClientProvider client={queryClient}>
      <section className={styles.wrapper}>
        <Navbar
          setHidden={handleHidden}
          setVisible={handleVisible}
          cheat={hiddenCount >= 3 ? true : false}
        />
        <div
          className={
            isOutletHidden ? `${styles.typeit}` : `${styles.typeit_hidden}`
          }
        >
          <TypeIt
            options={{ loop: true, speed: 350, loopDelay: 8000 }}
            getBeforeInit={(instance) => {
              instance
                .type("<Strong>BUGERKING</Strong>")
                .pause(2000)
                .delete(4)
                .pause(1000)
                .type("<Strong>PUT</Strong>")
                .pause(3000);
              return instance;
            }}
          />
          {/* {loading && (
            <Banner
              type={"loading"}
              text={
                <section
                  style={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <img
                    src='/spinner/spinner.gif'
                    width='35%'
                    style={{ paddingTop: "2px" }}
                  />
                  <h1
                    style={{
                      color: `white`,
                      whiteSpace: "nowrap",
                      marginTop: "1rem",
                      marginBottom: "5rem",
                    }}
                  >
                    Loading Initial Data...
                  </h1>
                </section>
              }
            />
          )}
          {result && (
            <Modal
              title={"로딩 실패"}
              component={"로딩에 실패했습니다. 다시 로딩하시겠습니까?"}
              setResult={setResult}
              error={true}
              submit={reLoad}
            />
          )} */}
          <div className={styles.burger_image}>
            <img
              className={styles.image}
              onClick={handleCount}
              src='/logo/burgerlogo2.png'
              alt='burger'
            />
          </div>
        </div>
        <div
          className={
            isOutletHidden ? `${styles.outlet_hidden}` : `${styles.outlet}`
          }
        >
          <div className={styles.phone}>
            <div className={styles.screen}>
              <div className={styles.body}>
                <Outlet context={{ handleHidden }} />
              </div>
            </div>
          </div>
        </div>
        <div className={styles.footer}>
          <Footer />
        </div>
      </section>
    </QueryClientProvider>
  );
}

export default App;
