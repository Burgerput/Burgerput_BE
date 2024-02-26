import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Main from "./pages/Main";
import Admins from "./pages/Admins";
import CustomMachines from "./pages/CustomMachines";
import CustomFoods from "./pages/CustomFoods";
import SelectManagers from "./pages/SelectManagers";
import InputMachines from "./pages/InputMachines";
import InputFoods from "./pages/InputFoods";
import NotFound from "./pages/NotFound";
import Help from "./pages/Help";
import CustomMachineTemp from "./pages/CustomMachineTemp";
import CustomFoodTemp from "./pages/CustomFoodTemp";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <NotFound />,
    children: [
      { index: true, path: "/", element: <Main /> },
      { path: "address", element: <Admins /> },
      { path: "select/machines", element: <CustomMachines /> },
      { path: "select/foods", element: <CustomFoods /> },
      { path: "select/managers", element: <SelectManagers /> },
      { path: "zenput/machines", element: <InputMachines /> },
      { path: "zenput/foods", element: <InputFoods /> },
      { path: "cheat/help", element: <Help /> },
      { path: "cheat/machine", element: <CustomMachineTemp /> },
      { path: "cheat/food", element: <CustomFoodTemp /> },
    ],
  },
  {
    path: "*",
    element: <App />,
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<RouterProvider router={router} />);
