import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  getMachines,
  setCustomMachines,
  getFoods,
  setCustomFoods,
  getCustomMachines,
  submitMachines,
  getCustomFoods,
  submitFoods,
} from "../api/Products";
import { useState } from "react";

export function useMachines() {
  const queryClient = useQueryClient();
  const [products, setProducts] = useState([]);
  const [success, setSuccess] = useState();

  const productsQuery = useQuery(["machines"], getMachines, {
    staleTime: Infinity,
    cacheTime: Infinity,
  });

  const addCustomMachines = useMutation(
    ({ products }) => setCustomMachines(products),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["customMachines"]);
        queryClient.invalidateQueries(["customMachinesTemp"]);
        queryClient.invalidateQueries(["machines"]);
      },
    }
  );

  const handleSubmit = (e) => {
    e.preventDefault();
    addCustomMachines.mutate(
      { products },
      {
        onSuccess: () => {
          setSuccess(true);
          setTimeout(() => {
            setSuccess(null);
          }, 4000);
        },
      }
    );
  };

  return { productsQuery, handleSubmit, success, setProducts };
}

export function useFoods() {
  const [success, setSuccess] = useState();
  const [products, setProducts] = useState([]);
  const queryClient = useQueryClient();

  const productsQuery = useQuery(["foods"], getFoods, {
    staleTime: Infinity,
    cacheTime: Infinity,
  });

  const addCustomFoods = useMutation(
    ({ products }) => setCustomFoods(products),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["customFoods"]);
        queryClient.invalidateQueries(["customFoodsTemp"]);
        queryClient.invalidateQueries(["foods"]);
      },
    }
  );

  const handleSubmit = (e) => {
    e.preventDefault();
    addCustomFoods.mutate(
      { products },
      {
        onSuccess: () => {
          setSuccess(true);
          setTimeout(() => {
            setSuccess(null);
          }, 4000);
        },
      }
    );
  };

  return { productsQuery, setProducts, success, handleSubmit };
}

export function useCustomProducts({ location, setProductsTemp }) {
  const [selectManager, setSelectManager] = useState("");
  const [result, setResult] = useState(false);
  const [loading, setLoading] = useState(false);
  const [products, setProducts] = useState([]);
  const [warning, setWarning] = useState(false);

  const handleWarning = () => {
    setWarning(true);
    setTimeout(() => {
      setWarning(false);
    }, 1500);
  };

  const handleSubmit = (e) => {
    e && e.preventDefault();
    const hasEmptyTemp = products.some((product) => !product.temp);

    if (hasEmptyTemp || selectManager.length === 0) {
      handleWarning();
      return;
    } else {
      setLoading(true);

      setProductsTemp({
        selectManager,
        products,
        location,
      })
        .then((res) => {
          setResult(res.data);
        })
        .finally(() => setLoading(false))
        .catch((error) => console.log(error));
    }
  };

  return {
    selectManager,
    setSelectManager,
    handleSubmit,
    warning,
    result,
    loading,
    products,
    setProducts,
    setResult,
  };
}

export function useCustomMachines() {
  const productsQuery = useQuery(["customMachines"], getCustomMachines, {
    staleTime: Infinity,
    cacheTime: Infinity,
  });

  const setProductsTemp = ({ selectManager, products, location }) =>
    submitMachines({
      mgrname: selectManager?.label,
      customMachine: products,
      time: location?.state,
    });

  return { productsQuery, setProductsTemp };
}

export function useCustomFoods() {
  const productsQuery = useQuery(["customFoods"], getCustomFoods, {
    staleTime: Infinity,
    cacheTime: Infinity,
  });

  const setProductsTemp = ({ selectManager, products, location }) =>
    submitFoods({
      mgrname: selectManager?.label,
      customFood: products,
      time: location?.state,
    });

  return { productsQuery, setProductsTemp };
}
