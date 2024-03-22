// 마이페이지
// 내 정보, 내 선호도(워드 클라우드), 내가 본 기사, 좋아요 한 기사, 
import { useState, useEffect } from "react"

import MyCategory from "../components/user/MyCategory"
import MyNews from "../components/user/MyNews"
import LikeNews from "../components/user/LikeNews"

import { recordDataState, likeDataState } from "../state/atoms"
import { useRecoilState } from "recoil"

import { getRecords, getLikes } from "../api/fetch"


export default function MyPage() {
  // 최근 본 뉴스 기록
  const [recordDatas, setRecordDatas] = useRecoilState(recordDataState)

  // 좋아요 한 뉴스 기록
  const [likeDatas, setLikeDatas] = useRecoilState(likeDataState)

  useEffect(()=>{
    getRecords(
      ( response ) => {
        console.log(response.data.data)
        setRecordDatas(response.data.data)
      },
      ( error ) => {
        console.log(error)
      }
    )
  },[])

  useEffect(()=>{
    getLikes(
      ( response ) => {
        console.log(response)
        setLikeDatas(response.data.data)
      },
      ( error ) => {
        console.log(error)
      }
    )
  },[])

  return (
    <div>
      <MyCategory/>
      <MyNews/>
      <LikeNews/>
    </div>
  )
}