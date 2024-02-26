import { useRef, useState } from "react";
import {
  getCustomTempFood,
  getCustomTempMachine,
  setCustomTempFood,
  setCustomTempMachine,
  submitFoods,
  submitMachines,
} from "../api/Products";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useRandomTemp } from "./useRandomTemp";

export function useCheatProducts({ setCustomTemp, submitCustomTemp }) {
  const [products, setProducts] = useState("");
  const [selectManager, setSelectManager] = useState("");
  const [result, setResult] = useState(false);
  const [status, setStatus] = useState({
    warning: null,
    success: false,
    loading: false,
  });

  const setTime = useRef();
  const { generateRandomTemp } = useRandomTemp();

  const handleWarning = (type) => {
    type === "missing" &&
      setStatus((prev) => ({ ...prev, warning: "missing" }));
    type === "manager" &&
      setStatus((prev) => ({ ...prev, warning: "manager" }));
    setTimeout(() => {
      setStatus((prev) => ({ ...prev, warning: null }));
    }, 1500);
  };

  const handleSave = (e) => {
    e.preventDefault();

    const hasMissing = products.some((product) => product.min === 999);

    if (hasMissing) {
      handleWarning("missing");
      return;
    } else {
      setCustomTemp.mutate(
        { products },
        {
          onSuccess: () => {
            setStatus((prev) => ({ ...prev, success: true }));
            setTimeout(() => {
              setStatus((prev) => ({ ...prev, success: false }));
            }, 4000);
          },
        }
      );
    }
  };

  const handleSubmit = (e) => {
    e && e.preventDefault();

    const newProducts = generateRandomTemp(products);
    const manager = selectManager?.label;
    const time = setTime?.current;

    if (!manager) {
      handleWarning("manager");
      return;
    } else {
      setStatus((prev) => ({ ...prev, loading: true }));
      submitCustomTemp({
        manager,
        newProducts,
        time,
      })
        .then((res) => {
          setResult(res.data);
        })
        .finally(() => setStatus((prev) => ({ ...prev, loading: false })))
        .catch(console.error);
    }
  };

  return {
    handleSave,
    handleSubmit,
    setProducts,
    setSelectManager,
    setResult,
    selectManager,
    products,
    status,
    result,
    setTime,
  };
}

export function useCheatMachines() {
  const { data } = useQuery(["customMachinesTemp"], getCustomTempMachine, {
    staleTime: Infinity,
    cacheTime: Infinity,
  });
  const queryClient = useQueryClient();

  const setCustomTemp = useMutation(
    ({ products }) => setCustomTempMachine(products),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["customMachinesTemp"]);
      },
    }
  );

  const submitCustomTemp = ({ manager, newProducts, time }) =>
    submitMachines({
      mgrname: manager,
      customMachine: newProducts,
      time: time,
    });

  return { data, setCustomTemp, submitCustomTemp };
}

export function useCheatFoods() {
  const { data } = useQuery(["customFoodsTemp"], getCustomTempFood, {
    staleTime: Infinity,
    cacheTime: Infinity,
  });
  const queryClient = useQueryClient();

  const setCustomTemp = useMutation(
    ({ products }) => setCustomTempFood(products),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["customFoodsTemp"]);
      },
    }
  );

  const submitCustomTemp = ({ manager, newProducts, time }) =>
    submitFoods({
      mgrname: manager,
      customFood: newProducts,
      time: time,
    });

  return { data, setCustomTemp, submitCustomTemp };
}
