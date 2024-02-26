import axios from "axios";
axios.defaults.timeout = 600 * 1000;

export async function getCurrentItems() {
  return await axios
    .get("/loading")
    .then((res) => res.data)
    .catch((error) => console.log(error));
}


