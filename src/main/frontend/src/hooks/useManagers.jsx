import React, { useState } from "react";
import { useQuery, useQueryClient, useMutation } from "@tanstack/react-query";
import {
  addManager,
  deleteManger,
  getAccounts,
  getManagerList,
  submitAccounts,
} from "../api/Managers";

export function useManagers() {
  const queryClient = useQueryClient();
  const [manager, setManager] = useState("");
  const managersQuery = useQuery(["managers"], () => getManagerList(), {
    staleTime: Infinity,
    cacheTime: Infinity,
  });

  const addMgr = useMutation(
    ({ manager }) => addManager([{ mgrname: manager }]),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["managers"]);
        queryClient.invalidateQueries(["customMachines"]);
        queryClient.invalidateQueries(["customFoods"]);
      },
    }
  );

  const delMgr = useMutation(
    ({ id, manager }) => deleteManger([{ id, mgrname: manager }]),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["managers"]);
        queryClient.invalidateQueries(["customMachines"]);
        queryClient.invalidateQueries(["customFoods"]);
      },
    }
  );

  const handleSubmit = (e) => {
    e.preventDefault();
    addMgr.mutate({ manager });
    setManager("");
  };

  const handleChange = (e) => {
    setManager(e.target.value);
  };

  const handleDelete = (id, mgrname) => {
    delMgr.mutate({ id, mgrname });
  };

  return { managersQuery, manager, handleChange, handleSubmit, handleDelete };
}

export function useAccounts() {
  const queryClient = useQueryClient();
  const [hide, setHide] = useState(true);
  const [success, setSuccess] = useState();

  const accountsQuery = useQuery(["accounts"], () => getAccounts(), {
    staleTime: Infinity,
    cacheTime: Infinity,
  });

  const submit = useMutation(
    ({ data }) =>
      submitAccounts({
        zenputId: data.email,
        rbiId: data.id,
        rbiPw: data.password,
      }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["accounts"]);
      },
    }
  );

  return { accountsQuery, submit, hide, setHide, success, setSuccess };
}
