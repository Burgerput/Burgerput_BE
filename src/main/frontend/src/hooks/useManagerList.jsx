export function useManagerList({ mgrList, setSelectManager }) {
  const options = mgrList.map(({ id, mgrname }) => ({
    value: id,
    label: mgrname,
  }));

  const customStyles = {
    option: (defaultStyles, state) => ({
      ...defaultStyles,
      fontWeight: "bold !important",
      color: state.isSelected
        ? "#323232"
        : state.isFocused
        ? "#323232"
        : "#d1d1d1",
      backgroundColor: state.isSelected
        ? "#d1d1d1"
        : state.isFocused
        ? "#d1d1d1"
        : "#323232",
    }),

    control: (defaultStyles) => ({
      ...defaultStyles,
      backgroundColor: "#323232",
      border: "none",
      boxShadow: "none",
      borderRadius: 5,
    }),
    singleValue: (defaultStyles) => ({ ...defaultStyles, color: "#d1d1d1" }),
  };

  const handleChange = (e) => {
    if (e.value === "직접입력") {
      setSelectManager(null);
    } else {
      setSelectManager(e);
    }
  };

  return { options, customStyles, handleChange };
}
