import axios from "axios";
axios.defaults.timeout = 600 * 1000;

// 달라진 목록을 받아오는 api
export async function getDiffInven() {
  return await axios
    .get("/back/main")
    .then((res) => res.data)
    .catch((error) => console.log(error));
}

// 기기 목록을 받아오는 api
export async function getMachines() {
  return await axios
    .get("/back/select/machines")
    .then((res) => res.data)
    .catch((error) => console.log(error));
}

// 식품 목록을 받아오는 api
export async function getFoods() {
  return await axios
    .get("/back/select/foods")
    .then((res) => res.data)
    .catch((error) => console.log(error));
}

// 선택한 기기 목록을 전송하는 api
export async function setCustomMachines(customMachines) {
  await axios.post("/back/select/machines", JSON.stringify(customMachines), {
    headers: { "Content-Type": "application/json" },
  });
}

// 선택한 식품 목록을 전송하는 api
export async function setCustomFoods(customFoods) {
  await axios.post("/back/select/foods", JSON.stringify(customFoods), {
    headers: { "Content-Type": "application/json" },
  });
}

// 선택한 기기 목록을 받아오는 api
export async function getCustomMachines() {
  return await axios
    .get("/back/enter/machines")
    .then((res) => res.data)
    .catch((error) => console.log(error));
}

// 완료된 기기 온도 값을 전달하는 api
export async function submitMachines(machines) {
  return await axios.post("/back/enter/machines", JSON.stringify(machines), {
    headers: { "Content-Type": "application/json" },
  });
}

// 선택한 식품 목록을 받아오는 api
export async function getCustomFoods() {
  return await axios
    .get("/back/enter/foods")
    .then((res) => res.data)
    .catch((error) => console.log(error));
}

// 완료된 식품 온도 값을 전달하는 api
export async function submitFoods(foods) {
  return await axios.post("/back/enter/foods", JSON.stringify(foods), {
    headers: { "Content-Type": "application/json" },
  });
}

// 기기 최종 제출을 요청하는 api
export async function submitResultMachines() {
  await axios.get("/back/msubmit");
}

// 식품 최종 제출을 요청하는 api
export async function submitResultFoods() {
  await axios.get("/back/fsubmit");
}

// 저장된 기기 온도 범위를 요청하는 api
export async function getCustomTempMachine() {
  return await axios
    .get("/back/cheatMachine")
    .then((res) => res.data)
    .catch((error) => console.error(error));
}

// 저장된 식품 온도 범위를 요청하는 api
export async function getCustomTempFood() {
  return await axios
    .get("/back/cheatFood")
    .then((res) => res.data)
    .catch((error) => console.error(error));
}

// 지정한 기기 온도 범위를 저장하는 api
export async function setCustomTempMachine(machines) {
  await axios.post("/back/cheatMachine", JSON.stringify(machines), {
    headers: { "Content-Type": "application/json" },
  });
}

// 지정한 식품 온도 범위를 저장하는 api
export async function setCustomTempFood(foods) {
  await axios.post("/back/cheatFood", JSON.stringify(foods), {
    headers: { "Content-Type": "application/json" },
  });
}
