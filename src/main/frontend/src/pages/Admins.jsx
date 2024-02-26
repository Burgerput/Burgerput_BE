import React, { useState } from "react";
import styles from "./Admins.module.css";
import { useForm } from "react-hook-form";
import { useNavigate, useOutletContext } from "react-router-dom";
import { AiOutlineEyeInvisible, AiOutlineEye } from "react-icons/ai";
import Banner from "./../components/Banner";
import { useAccounts } from "./../hooks/useManagers";
import Button from "../components/Button";

export default function Admins() {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting, isSubmitted, errors },
  } = useForm({ mode: "onChange" });

  const {
    accountsQuery: { isLoading, error, data: accounts },
    submit,
    hide,
    setHide,
    success,
    setSuccess,
  } = useAccounts();

  const { handleHidden } = useOutletContext();
  const navigate = useNavigate();
  const handleClick = () => {
    handleHidden();
    navigate("/");
  };

  return (
    <section className={styles.section}>
      <div className={styles.header}>
        <div className={styles.title}>관리자 정보 입력</div>
      </div>
      <form
        id='inputForm'
        className={styles.form}
        onSubmit={handleSubmit(async (data) => {
          await new Promise((r) => setTimeout(r, 1000));
          submit.mutate(
            { data },
            {
              onSuccess: () => {
                setSuccess(true);
                setTimeout(() => {
                  setSuccess(null);
                }, 4000);
              },
            }
          );
        })}
      >
        <div className={styles.inputBox}>
          {success && <Banner text={"계정 저장이 완료되었습니다."} />}
          <label className={styles.label} htmlFor='email'>
            젠풋 이메일
          </label>
          <div className={styles.wrapper}>
            <input
              className={styles.input}
              defaultValue={accounts ? accounts.zenputId : null}
              id='email'
              type='email'
              placeholder='example@email.com'
              autoComplete='off'
              aria-invalid={
                isSubmitted ? (errors.email ? true : false) : undefined
              }
              {...register("email", {
                required: "이메일은 필수 입력 사항입니다.",
                pattern: {
                  value: /\S+@\S+\.\S+/,
                  message: "이메일 형식에 맞지 않습니다.",
                },
              })}
            />
            {errors.email && (
              <small className={styles.error} role='alert'>
                {errors.email.message}
              </small>
            )}
          </div>
        </div>

        <div className={styles.inputBox}>
          <label className={styles.label} htmlFor='id'>
            rbi 아이디
          </label>
          <div className={styles.wrapper}>
            <input
              className={styles.input}
              defaultValue={accounts ? accounts.rbiId : null}
              id='id'
              type='id'
              placeholder='your ID'
              autoComplete='off'
              {...register("id", {
                required: "아이디는 필수 입력 사항입니다.",
              })}
            />
            {errors.id && (
              <small className={styles.error} role='alert'>
                {errors.id.message}
              </small>
            )}
          </div>
        </div>

        <div className={styles.inputBox}>
          <label className={styles.label} htmlFor='password'>
            rbi 비밀번호
          </label>
          <div className={styles.wrapper}>
            <input
              className={styles.input}
              defaultValue={accounts ? accounts.rbiPw : null}
              id='password'
              type={hide ? `password` : "text"}
              placeholder='********'
              autoComplete='off'
              {...register("password", {
                required: "비밀번호는 필수 입력 사항입니다.",
                minLength: {
                  value: 6,
                  message: "비밀번호 형식에 맞지 않습니다.",
                },
              })}
            />
            {hide ? (
              <button
                type='button'
                className={styles.toggleButton}
                onClick={(e) => setHide(false)}
              >
                <AiOutlineEye />
              </button>
            ) : (
              <button
                type='button'
                className={styles.toggleButton}
                onClick={() => setHide(true)}
              >
                <AiOutlineEyeInvisible />
              </button>
            )}
          </div>
          {errors.password && (
            <small className={styles.error} role='alert'>
              {errors.password.message}
            </small>
          )}
        </div>
      </form>
      <div className={styles.buttons}>
        <Button
          text={"저장"}
          type={"submit"}
          form={"inputForm"}
          disabled={isSubmitting}
        />
        <Button text={"취소"} handleFunction={handleClick} />
      </div>
    </section>
  );
}
