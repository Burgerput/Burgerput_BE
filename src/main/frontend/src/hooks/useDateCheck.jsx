import { useState } from "react";
import { getCurrentItems } from "../api/Loading";

export function useDateCheck() {
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(false);

  // 현재 날짜 객체를 반환하는 함수
  function setCurrentDate() {
    return new Date();
  }

  // 오전 8시에 해당하는 시간을 설정해 반환하는 함수
  function getComparisonDate() {
    const comparisonDateHour = new Date();
    comparisonDateHour.setHours(8, 0, 0, 0); // 시, 분, 초, 밀리초 설정
    return comparisonDateHour;
  }

  // 8시 이전, 이후를 비교해 boolean 값을 반환하는 함수
  function isAfter8AM() {
    const currentDate = setCurrentDate();
    const comparisonDate = getComparisonDate();

    return currentDate.getTime() >= comparisonDate.getTime();
  }

  // 현재 시간을 년,월,일로 배열에 저장해 반환하는 함수
  function getCurrentDate() {
    const currentDate = setCurrentDate();

    const year = currentDate.getFullYear();
    const month = +("0" + (1 + currentDate.getMonth())).slice(-2);
    const date = +("0" + currentDate.getDate()).slice(-2);

    return [year, month, date];
  }

  // 로컬 스토리지에 현재 시간을 저장하고, loading 페이지로 이동하는 함수
  function saveCurrentDate() {
    setLoading(true);

    const currentDate = getCurrentDate();
    localStorage.setItem("currentDate", JSON.stringify(currentDate));

    getCurrentItems()
      .then((res) => {
        console.log(res);
        !res && setResult(true);
      })
      .finally(() => setLoading(false));
  }
  // 날짜 비교 함수
  function checkDate() {
    const savedDate = JSON.parse(localStorage.getItem("currentDate"));
    const currentDate = getCurrentDate();

    // 저장된 날짜 배열을 reduce로 순회함 현재 날짜가 저장된 날짜 보다 년,월,일 중 하나라도 클 경우 result에 1을 추가함
    const result = savedDate
        ? savedDate.reduce((result, date, idx) => {
          if (date < currentDate[idx]) {
            return (result += 1);
          }

          return result;
        }, 0)
        : 1;

    if (result === 0) {
      return;
    }

    if (result > 0) {
      if (isAfter8AM()) {
        saveCurrentDate();
      } else {
        return;
      }
    }
  }

  function reLoad() {
    saveCurrentDate();
  }

  return { checkDate, reLoad, setResult, loading, result };
}
