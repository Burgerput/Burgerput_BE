import axios from "axios";

export async function getManagerList() {
  return await axios
    .get("/back/managers")
    .then((res) => res.data)
    .catch((error) => console.log(error));
}

export async function addManager(manager) {
  await axios.post("/back/managers", JSON.stringify(manager), {
    headers: { "Content-Type": "application/json" },
  });
}

export async function deleteManger(manager) {
  await axios.post("/back/manager", JSON.stringify(manager), {
    headers: { "Content-Type": "application/json" },
  });
}

export async function getAccounts() {
  return await axios
    .get("/back/accounts")
    .then((res) => res.data)
    .catch((error) => console.log(error));
}

export async function submitAccounts(accounts) {
  await axios.post("/back/accounts", JSON.stringify(accounts), {
    headers: { "Content-Type": "application/json" },
  });
}
